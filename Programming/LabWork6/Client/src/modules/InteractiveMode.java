package modules;

import callers.specialClientCaller;
import descriptions.CommandDescription;
import descriptions.LoadDescription;
import loaders.ConsoleLoader;
import result.Result;
import specialDescriptions.ExecuteScriptDescription;
import specialDescriptions.ExitDescription;
import specialDescriptions.HistoryDescription;
import java.net.DatagramPacket;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;

public class InteractiveMode {
    private static InteractiveMode interactiveMode;
    private CallableManager callableManager;
    private TextReceiver textReceiver;
    private ConsoleLoader loader;
    private ObjectSender objectSender;
    private RequestHandler requestHandler;

    private boolean isAuthorized = false;

    private Map<String, CommandDescription> commandDescriptionMap;

    private static Map<String, Runnable> authorizationMap;
    private static Map<String, CommandDescription> specialCommands;

    static {
        specialCommands.put("exit", new ExitDescription(interactiveMode.objectSender));
        specialCommands.put("execute_script", new ExecuteScriptDescription(interactiveMode.callableManager,interactiveMode.objectSender));
        specialCommands.put("history", new HistoryDescription(interactiveMode.objectSender));
    }

    static {
        authorizationMap.put("r", interactiveMode::register);
        authorizationMap.put("l", interactiveMode::login);
        authorizationMap.put("q", interactiveMode::exit);
    }

    private ArrayDeque<String> history;

    private InteractiveMode(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        this.textReceiver = textReceiver;
        this.loader = loader;
        this.requestHandler = requestHandler;
        this.callableManager = callableManager;

    }

    public static InteractiveMode getInstance(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        if (interactiveMode == null) {
            interactiveMode = new InteractiveMode(textReceiver, loader, requestHandler, objectSender, callableManager);
        }
        return interactiveMode;
    }

    public static InteractiveMode getInstance() {
        return interactiveMode;
    }

    private Map<String, CommandDescription> loadCommandDescriptionMap() {
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

    public Map<String, CommandDescription> getCommandDescriptionMap() {
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
                Result<?> resultOfExecuting = callableManager.callAll().get(0);
                if (!resultOfExecuting.isSuccess()) {
                    textReceiver.println(resultOfExecuting.getMessage());
                }else{
                    history.add(command.getName());
                }
                callableManager.clear();
            }
        }

    private void register() {
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

    public void history() {
        history.stream().limit(6).forEach(textReceiver::println);
    }

    private void login() {
        textReceiver.print("Enter your login:");
        String login = (String) loader.enter(new LoadDescription(String.class)).getValue();
        textReceiver.print("Enter your password:");
        String password = (String) loader.enter(new LoadDescription(String.class)).getValue();
        //отправить на сервер
        //получить ответ
        //если ответ положительный, то
        isAuthorized = true;
    }

    public void exit() {
        System.exit(0);
    }

    public boolean isAuthorized () {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public void printHelp() {
        textReceiver.println("Available commands:");
        commandDescriptionMap.values().stream()
                .forEach(commandDescription -> textReceiver.println(commandDescription.getName() + " - " + commandDescription.getDescription()));
    }

}
