package managers.connection;

import commands.HelpCommand;
import commands.LoginCommand;
import commands.RegisterCommand;
import common.descriptions.CommandDescription;
import managers.Invoker;
import managers.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import result.Result;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class InputController {
    private static final Logger logger = LogManager.getLogger(InputController.class);

    private final int DISCONNECTING_TIMEOUT = 5;

    ResultSender rs = null;
    DatagramSocket ds;

    private final Map<String, BiFunction<User, DatagramPacket, Void>> user_connection_commands;

    public InputController(DatagramSocket ds) {
        user_connection_commands = new HashMap<>();
        user_connection_commands.put("login", this::login);
        user_connection_commands.put("register", this::register);
        this.ds = ds;
    }

    public void parse(CommandDescription cd, Invoker invoker, DatagramPacket dp) {
        logger.info("Incoming request");
        Result<?> RESULT = null;
        if (user_connection_commands.containsKey(cd.getName())) {
            if (cd.getOneLineArguments().size() == 2)
                user_connection_commands.get(cd.getName()).apply(new User(
                        (String) cd.getOneLineArguments().get(0).getValue(),
                        (String) cd.getOneLineArguments().get(1).getValue(),
                        dp.getAddress(),
                        dp.getPort()
                ), dp);
            else {
                logger.error("Incorrect request");
                ResultSender tmp_sender = new ResultSender(new User(null, null, dp.getAddress(), dp.getPort()), ds);
                RESULT = Result.failure(new Exception(""), "Ожидается ввод только 2 аргументов: логина и пароля");
                tmp_sender.send(RESULT);
            }
        } else {
            if (rs == null) {
                logger.error("Incorrect request: user didn't login");
                RESULT = Result.failure(new Exception(""), "Войдите в систему");
                ResultSender tmp_sender = new ResultSender(new User(null, null, dp.getAddress(), dp.getPort()), ds);
                tmp_sender.send(RESULT);
            } else if (rs.user.getPort() == dp.getPort() && rs.user.getHost() == dp.getAddress()) {
                if (cd.getName().equals("exit")) {
                    close_client();
                }

                RESULT = invoker.executeCommand(cd.getName(), cd);
                rs.send(RESULT);
            } else {
                RESULT = Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
                ResultSender tmp_sender = new ResultSender(new User(null, null, dp.getAddress(), dp.getPort()), ds);
                tmp_sender.send(RESULT);
            }
        }
    }


    private Void login(User user, DatagramPacket dp) {
        ResultSender sender = new ResultSender(new User(null, null, dp.getAddress(), dp.getPort()), ds);
        try {
            if (rs == null || LocalDateTime.now().minusMinutes(DISCONNECTING_TIMEOUT).isAfter(rs.user.getLastActivity())) {
                if ((boolean) (new LoginCommand(user.getLogin(), user.getPassword()).execute().getValue().get())) {
                    if (rs != null) {
                        rs.send(Result.success(null, "Вы отключены от сервера, так как бездействовали больше " + DISCONNECTING_TIMEOUT + " минут и подключился другой пользователь."));
                        close_client();
                    }
                    rs = new ResultSender(user, ds);
                    logger.info("New user connected");
                    rs.send(Result.success(new HelpCommand().execute().getValue().get(), "Вход выполнен успешно"));
                } else {
                    sender.send(Result.failure(new Exception(), "Логин или пароль неверны"));
                }
            } else
                sender.send(Result.failure(new Exception(), "Сервер занят занят работой с другим клиентом"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e); //если по неизвестной причине UserReceiver, инициализированный в Main, при GetInstance кинет исключение
        }
        return null;
    }

    private Void register(User user, DatagramPacket dp) {
        ResultSender sender = new ResultSender(new User(null, null, dp.getAddress(), dp.getPort()), ds);
        try {
            Result<Void> r = new RegisterCommand(user).execute();
            if (r.isSuccess()) {
                logger.info("New user registered");
                sender.send(Result.success(null, "Регистрация проведена. Теперь можно войти с этим же аккаунтом"));
            } else {
                logger.error(r.getMessage());
                sender.send(Result.failure(r.getError().get(), r.getMessage()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);   //если по неизвестной причине UserReceiver, инициализированный в Main, при GetInstance кинет исключение
        }
        return null;
    }

    public void close_client() {
        logger.info("User disconnected");
        rs = null;
    }
}
