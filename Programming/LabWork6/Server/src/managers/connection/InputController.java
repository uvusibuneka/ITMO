package managers.connection;

import commands.HelpCommand;
import commands.LoginCommand;
import commands.RegisterCommand;
import common.descriptions.CommandDescription;
import managers.Invoker;
import managers.user.User;
import result.Result;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InputController {
    private final int DISCONNECTING_TIMEOUT = 5;

    ResultSender rs = null;

    private final Map<String, Function<User, Result<?>>> user_commands;

    public InputController() {
        user_commands = new HashMap<>();
        user_commands.put("login", this::login);
        user_commands.put("register", this::register);
    }

    public void parse(CommandDescription cd, Invoker invoker, DatagramPacket dp) {
        Result<?> res = null;
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
            } else if (rs.user.getPort() == dp.getPort() && rs.user.getHost() == dp.getAddress()) {
                if (cd.getName().equals("exit")) {
                    close_client();
                }

                res = invoker.executeCommand(cd.getName(), cd);
            } else {
                res = Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
            }
        }
        if (rs != null)
            rs.send(res);
    }


    private Result<HashMap<String, CommandDescription>> login(User user) {
        try {
            if (rs == null || LocalDateTime.now().minusMinutes(DISCONNECTING_TIMEOUT).isAfter(rs.user.getLastActivity())) {
                if ((boolean)
                        (new LoginCommand(user.getLogin(), user.getPassword())
                                .execute().getValue().get())
                ) {
                    try {
                        if (rs != null) {
                            rs.send(Result.success(null, "Вы отключены от сервера, так как бездействовали больше " + DISCONNECTING_TIMEOUT + " минут и подключился другой пользователь."));
                            close_client();
                        }
                        rs = new ResultSender(user);
                        return Result.success(new HelpCommand().execute().getValue().get(),
                                "Вход выполнен успешно");
                    } catch (SocketException e) {
                        System.out.println("Он не дождётся ответа...");
                        return Result.failure(e, "");
                    }
                } else
                    return Result.failure(new Exception(), "Логин или пароль неверны");
            } else
                return Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
        } catch (Exception e) {
            return Result.failure(e, e.getMessage());   //если по неизвестной причине UserReceiver, инициализированный в Main, при GetInstance кинет исключение
        }
    }

    private Result<Void> register(User user) {
        try {

            Result<Void> r = new RegisterCommand(user).execute();
            if (r.isSuccess())
                return Result.success(null, "Регистрация проведена. Теперь можно войти с этим же аккаунтом");
            else
                return Result.failure(r.getError().get(), r.getMessage());
        } catch (Exception e) {
            return Result.failure(e, e.getMessage());   //если по неизвестной причине UserReceiver, инициализированный в Main, при GetInstance кинет исключение
        }
    }

    public void close_client() {
        rs.ds.close();
        rs = null;
    }
}
