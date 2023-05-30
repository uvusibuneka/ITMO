package modules;

import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import loaders.ConsoleLoader;
import result.Result;
import specialDescriptions.ExecuteScriptDescription;
import specialDescriptions.ExitDescription;
import specialDescriptions.HelpDescription;
import specialDescriptions.HistoryDescription;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InteractiveMode {
    private static InteractiveMode interactiveMode;
    private CallableManager callableManager;
    private TextReceiver textReceiver;
    private ConsoleLoader loader;
    private ObjectSender objectSender;
    private RequestHandler requestHandler;

    private final CommandDescription loginCommandDescription = new CommandDescription("login", List.of(new LoadDescription(String.class), new LoadDescription(String.class)));

    private final CommandDescription registerCommandDescription = new CommandDescription("register", List.of(new LoadDescription(String.class), new LoadDescription(String.class)));
    private boolean isAuthorized = false;

    private Map<String, CommandDescription> commandDescriptionMap;

    private Map<String, Runnable> authorizationMap;
    private Map<String, CommandDescription> specialCommands = new HashMap<>();
    private ArrayDeque<String> history;


    private InteractiveMode(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        this.textReceiver = textReceiver;
        this.loader = loader;
        this.requestHandler = requestHandler;
        this.callableManager = callableManager;
        this.objectSender = objectSender;
        authorizationMap = Map.of("l", this::login, "r", this::register, "q", this::exit);
        specialCommands = Map.of("help", new HelpDescription(objectSender, this), "history", new HistoryDescription(objectSender, this), "execute_script", new ExecuteScriptDescription(callableManager, objectSender, this), "exit", new ExitDescription(objectSender, this));
    }

    public static InteractiveMode getInstance(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        if (interactiveMode == null) {
            interactiveMode = new InteractiveMode(textReceiver, loader, requestHandler, objectSender, callableManager);
        }
        return interactiveMode;
    }

    public static InteractiveMode getObject() {
        return interactiveMode;
    }

    private Result<Map<String, CommandDescription>> loadCommandDescriptionMap() {
        for (int i = 0; i < 5; i++) {
            try {
                DatagramPacket MapOfCommands = requestHandler.receivePacketWithTimeout();
                break;
            } catch (Exception e) {
                textReceiver.print("Error while receiving map of commands, error with server connection.\n Wait...");
                if(i == 4){
                    exit();
                    return Result.failure(e, "Error while receiving map of commands, error with server connection.");
                }
            }
        }

        try {
            DatagramPacket packet = requestHandler.receivePacketWithTimeout();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            commandDescriptionMap = (Map<String, CommandDescription>) objectInputStream.readObject();
            return Result.success(commandDescriptionMap);
        } catch (Exception e) {
            return Result.failure(e, "Error while receiving map of commands, error with server connection.");
        }
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
            } else {
                history.add(command.getName());
            }
            callableManager.clear();
        }
    }

    private void register() {
        enterLoginData(registerCommandDescription);
        try {
            objectSender.sendObject(registerCommandDescription);
            DatagramPacket packet = requestHandler.receivePacketWithTimeout();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Result<?> registerResult = loadCommandDescriptionMap();
            if (registerResult.isSuccess()) {
                textReceiver.println("You have successfully registered!");
            } else {
                textReceiver.println("Error while receiving map of commands, error with server connection.");
            }
        } catch (Exception e) {
            textReceiver.println("Error while sending login command to server, error with server connection.");
        }
        isAuthorized = true;
    }

    private void enterLoginData(CommandDescription registerCommandDescription) {
        textReceiver.print("Enter your login:");
        LoadDescription<String> loginDescription = loader.enterString(new LoadDescription<>(String.class));
        textReceiver.print("Enter your password:");
        LoadDescription<String> passwordDescription = loader.enterString(new LoadDescription<>(String.class));
        registerCommandDescription.setOneLineArguments(List.of(loginDescription, passwordDescription));
    }

    public void history() {
        history.stream().limit(6).forEach(textReceiver::println);
    }

    private void login() {
        enterLoginData(loginCommandDescription);
        try {
            objectSender.sendObject(loginCommandDescription);
            Result<?> commandMapResult = loadCommandDescriptionMap();
            isAuthorized = true;
        } catch (Exception e) {
            textReceiver.println("Error while sending login command to server, error with server connection.");
        }
    }

    public void exit() {
        System.exit(0);
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public void printHelp() {
        textReceiver.println("Available commands:");
        commandDescriptionMap.values()
                .forEach(commandDescription -> textReceiver.println(commandDescription.getName() + " - " + commandDescription.getDescription()));
    }

}
