package modules;

import descriptions.CommandDescription;
import descriptions.LoadDescription;
import loaders.ConsoleLoader;
import result.Result;

import java.net.DatagramPacket;
import java.util.ArrayDeque;
import java.util.Map;

public class InteractiveMode {
    private static InteractiveMode interactiveMode;
    private static CallableManager callableManager;
    private static TextReceiver textReceiver;
    private static ConsoleLoader loader;

    private static ObjectSender objectSender;
    private static RequestHandler requestHandler;
    private static boolean isAuthorized = false;

    private static Map<String, CommandDescription> commandDescriptionMap;

    private static Map<String, Runnable> authorizationMap;

    static {
        authorizationMap.put("r", InteractiveMode::register);
        authorizationMap.put("l", InteractiveMode::login);
        authorizationMap.put("q", InteractiveMode::exit);
    }

    private static ArrayDeque<String> history;

    private InteractiveMode(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        InteractiveMode.textReceiver = textReceiver;
        InteractiveMode.loader = loader;
        InteractiveMode.requestHandler = requestHandler;
        InteractiveMode.callableManager = callableManager;

    }

    public static InteractiveMode getInstance(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        if (interactiveMode == null) {
            interactiveMode = new InteractiveMode(textReceiver, loader, requestHandler, objectSender, callableManager);
        }
        return interactiveMode;
    }

    private Map<String, CommandDescription> getCommandDescriptionMap() {
        for (int i = 0; i < 5; i++) {
            try {
                DatagramPacket MapOfCommands = requestHandler.receivePacketWithTimeout();
                break;
            } catch (Exception e) {
                textReceiver.print("Error while receiving map of commands, error with server connection.\n Wait...");
            }
        }
        //десериализовать и получить карту коллекции из MapOfCommands
        Map<String, CommandDescription> commandDescriptionMap = null;

        return commandDescriptionMap;
    }

    public void start() {
            textReceiver.println("Welcome to interactive mode! Are you want to register or login? (r/l). Type \"q\" to exit.");
            while (true) {
                String command = (String) loader.enterWithMessage(">", new LoadDescription(String.class)).getValue();
                if (authorizationMap.containsKey(command)) {
                    authorizationMap.get(command).run();
                    break;
                } else {
                    textReceiver.println("Unknown command!");
                }
            }
            textReceiver.println("Interactive mode started! Check command help to see available commands.");
            Map<String, CommandDescription> commandDescriptionMap = getCommandDescriptionMap();
            while (true) {
                CommandDescription command = loader.parseCommand(commandDescriptionMap,
                        (String) loader.enterWithMessage(">", new LoadDescription(String.class)).getValue()
                );
                callableManager.add(command.getCaller());
                Result<Void> resultOfExecuting = callableManager.callAll();
                if (!resultOfExecuting.isSuccess()) {
                    textReceiver.println(resultOfExecuting.getMessage());
                }else{
                    history.add(command.getName());
                }
                callableManager.clear();
            }
        }

    private static void register() {
        textReceiver.println("Enter your login:");
        String login = (String) loader.enterWithMessage(">", new LoadDescription(String.class)).getValue();
        textReceiver.println("Enter your password:");
        String password = (String) loader.enterWithMessage(">", new LoadDescription(String.class)).getValue();
        //отправить на сервер
        //objectSender.sendObject();
        //получить ответ
        //если ответ положительный, то
        isAuthorized = true;
    }

    public static void history() {
        for (String command: history) {
            textReceiver.println(command);
        }
    }

    private static void login() {
        textReceiver.print("Enter your login:");
        String login = (String) loader.enter(new LoadDescription(String.class)).getValue();
        textReceiver.print("Enter your password:");
        String password = (String) loader.enter(new LoadDescription(String.class)).getValue();
        //отправить на сервер
        //получить ответ
        //если ответ положительный, то
        isAuthorized = true;
    }

    private static void exit() {
        System.exit(0);
    }

    public boolean isAuthorized () {
        return isAuthorized;
    }
}
