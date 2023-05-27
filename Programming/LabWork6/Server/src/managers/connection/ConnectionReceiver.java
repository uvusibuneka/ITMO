package managers.connection;

import commands.HelpCommand;
import commands.LoginCommand;
import commands.RegisterCommand;
import common.descriptions.CommandDescription;
import managers.Invoker;
import managers.user.User;
import result.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConnectionReceiver {
    private final int DISCONNECTING_TIMEOUT = 5;

    ResultSender rs;
    private final Map<String, Function<User, Result<?>>> user_commands;

    public ConnectionReceiver() {
        user_commands = new HashMap<>();
        user_commands.put("login", this::login);
        user_commands.put("register", this::register);
    }

    public void run(Invoker invoker) throws SocketException, NumberFormatException {
        byte[] arr = new byte[1024];
        int len = arr.length;
        DatagramSocket ds;
        DatagramPacket dp;
        InetAddress host;
        int port = Integer.parseInt(System.getenv("SERVER_PORT"));

        rs = null;

        ds = new DatagramSocket(port);

        Result<?> res = null;

        while (true) {
            dp = new DatagramPacket(arr, len);
            try {
                ds.receive(dp);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(dp.getData());
                ObjectInputStream ois = new ObjectInputStream(byteStream);
                CommandDescription cd = (CommandDescription) ois.readObject();

                if (user_commands.containsKey(cd.getName())) {
                    if (cd.getOneLineArguments().size() == 2)
                        res = user_commands.get(cd.getName()).apply(new User(
                            (String) cd.getOneLineArguments().get(0).getValue(),
                            (String) cd.getOneLineArguments().get(1).getValue(),
                            dp.getAddress(), dp.getPort()));
                    else
                        res = Result.failure(new Exception(""), "Ожидается ввод только 2 аргументов: логина и пароля");
                } else {
                    if (rs == null) {
                        res = Result.failure(new Exception(""), "Войдите в систему");
                    } else
                    if (rs.user.getPort() == dp.getPort() && rs.user.getHost() == dp.getAddress()) {
                        if (cd.getName().equals("exit")) {
                            close_client();
                            continue;
                        }

                        res = invoker.executeCommand(cd.getName(), cd);
                    } else {
                        res = Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
                    }
                }
                if (rs != null)
                    rs.send(res);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Result<HashMap<String, CommandDescription>> login(User user) {
        if (rs == null || LocalDateTime.now().minusMinutes(DISCONNECTING_TIMEOUT).isAfter(rs.user.getLastActivity())) {
            if ((boolean)
                    (new LoginCommand( user.getLogin(), user.getPassword())
                    .execute().getValue().get())
            ) {
                try {
                    if (rs != null)
                        rs.send(Result.success(null, "Вы отключены от сервера, так как бездействовали больше " + DISCONNECTING_TIMEOUT + " минут и подключился другой пользователь."));
                    rs = new ResultSender(user);
                    return Result.success(new HelpCommand().execute().getValue().get(),
                            "Вход выполнен успешно");
                } catch (SocketException e) {
                    System.out.println("Он не дождётся ответа...");
                    return Result.failure(e, "");
                }
            }
            else
                return Result.failure(new Exception(), "Логин или пароль неверны");
        }
        else
            return Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
    }

    private Result<Void> register(User user) {
        Result<Void> r = new RegisterCommand(user).execute();
        if (r.isSuccess())
            return Result.success(null, "Регистрация проведена. Теперь можно войти с этим же аккаунтом");
        else
            return Result.failure(r.getError().get(), r.getMessage());
    }

    public void close_client() {
        rs = null;
    }
}
