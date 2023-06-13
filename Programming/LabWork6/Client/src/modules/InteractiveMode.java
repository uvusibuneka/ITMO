package modules;

import callers.ServerCommandCaller;
import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import loaders.ConsoleLoader;
import result.Result;
import specialDescription.ExecuteScriptDescription;
import specialDescription.ExitDescription;
import specialDescription.HelpDescription;
import specialDescription.HistoryDescription;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class InteractiveMode {
    private static InteractiveMode interactiveMode;
    private CallableManager callableManager;
    private TextReceiver textReceiver;
    private ConsoleLoader loader;
    private ObjectSender objectSender;

    public ObjectSender getObjectSender() {
        return objectSender;
    }

    public void setObjectSender(ObjectSender objectSender) {
        this.objectSender = objectSender;
    }

    private RequestHandler requestHandler;
    private final CommandDescription loginCommandDescription = new CommandDescription("login", "Вход пользователя", List.of(new LoadDescription(String.class), new LoadDescription(String.class)));

    private final CommandDescription registerCommandDescription = new CommandDescription("register", "Регистрация пользователя", List.of(new LoadDescription(String.class), new LoadDescription(String.class)));
    private boolean isAuthorized = false;

    private Map<String, CommandDescription> commandDescriptionMap;

    private ExitDescription exitDescription;

    private Map<String, Supplier<Result<?>>> authorizationMap = new HashMap<>();

    public Map<String, CommandDescription> getSpecialCommands() {
        return specialCommands;
    }

    private Map<String, CommandDescription> specialCommands = new HashMap<>();
    private ArrayDeque<String> history = new ArrayDeque<>();

    private InteractiveMode(TextReceiver textReceiver, ConsoleLoader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        this.textReceiver = textReceiver;
        this.loader = loader;
        this.requestHandler = requestHandler;
        this.callableManager = callableManager;
        this.objectSender = objectSender;
        authorizationMap = Map.of("l", this::login, "r", this::register, "q", this::exit);
        specialCommands = Map.of(
                "help", new HelpDescription(objectSender, this),
                "history", new HistoryDescription(objectSender, this),
                "execute_script", new ExecuteScriptDescription(callableManager, objectSender, this),
                "exit", new ExitDescription(objectSender, this)
        );
        exitDescription = new ExitDescription(objectSender, this);
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



    @SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent"})
    private Result<HashMap<String, CommandDescription>> loadCommandDescriptionMap() {
        try {
            Result<DatagramPacket> packet = requestHandler.receivePacketWithTimeout();
            if (!packet.isSuccess()) {
                return Result.failure(packet.getError().get(), "Error while receiving map of commands, error with server connection.");
            }
            Result<HashMap<String, CommandDescription>> commandDescriptionMap = null;
            try {
                commandDescriptionMap = deserialize(packet.getValue().get());
            } catch (Exception e) {
                textReceiver.println(e);
                throw new RuntimeException(e);
            }
            if (!commandDescriptionMap.isSuccess()) {
                return commandDescriptionMap;
            }
            textReceiver.println("You are authorized!");
            this.commandDescriptionMap = commandDescriptionMap.getValue().get();
            return commandDescriptionMap;
        } catch (Exception e) {
            return Result.failure(e, "Error while receiving map of commands, error with server connection.");
        }
    }

    public <T> T deserialize(DatagramPacket packet) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (T) objectInputStream.readObject();
    }

    public Map<String, CommandDescription> getCommandDescriptionMap() {
        return commandDescriptionMap;
    }

    public void start() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (InteractiveMode.getObject().isAuthorized())
                try {
                    exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            System.out.println("Программа завершает работу.");
        }));

        textReceiver.println("Welcome to interactive mode! Are you want to register or login? (r/l). Type \"q\" to exit.");
        while (true) {
            String command = (String) loader.enterWithMessage(">", new LoadDescription<String>(String.class)).getValue();
            if (authorizationMap.containsKey(command)) {
                Result<?> result = authorizationMap.get(command).get();
                if (result.isSuccess() && command.equals("l")) {
                    break;
                }
                textReceiver.println(result.getMessage());
            } else {
                textReceiver.println("Unknown command!");
            }
        }


        textReceiver.println("Interactive mode started! Check command help to see available commands.");
        Map<String, CommandDescription> commandDescriptionMap = getCommandDescriptionMap();
        while (true) {
            CommandDescription command = null;
            try {
                command = loader.parseCommand(commandDescriptionMap,
                        (String) loader.enterWithMessage(">", new LoadDescription<String>(String.class)).getValue()
                );
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
                continue;
            }
            if (this.isSpecial(command.getName())) {
                command.setCaller(specialCommands.get(command.getName()).getCaller());
                callableManager.addSpecial(specialCommands.get(command.getName()).getCaller());
                try {
                    command.getCaller().call();
                } catch (Exception e){
                    System.out.println("Error with calling of command. " + e.getMessage());
                }
                continue;
            } else {
                command.setCaller(new ServerCommandCaller(command, objectSender));
                callableManager.add(command.getCaller());
            }

            var resultOfExecuting = callableManager.callFirst();
            textReceiver.println(resultOfExecuting.getMessage());
            if (resultOfExecuting.isSuccess()) {
                if (!command.getName().equals("help") && resultOfExecuting.getValue().isPresent())
                    textReceiver.println(resultOfExecuting.getValue().get());
                history.add(command.getName());
            }
            callableManager.clear();
        }
    }

    private Result<Void> register() {
        enterLoginData(registerCommandDescription);
        try {
            try {
                objectSender.sendObject(registerCommandDescription);
            } catch (IOException ex) {
                throw new RuntimeException("Error while sending answer, error with server connection.");
            }

            Result<DatagramPacket> packet = requestHandler.receivePacketWithTimeout();
            if (!packet.isSuccess()) {
                return Result.failure(packet.getError().get(), "Error while receiving answer, error with server connection.");
            }
            Result<Void> registerResult;
            try {
                registerResult = deserialize(packet.getValue().get());
            } catch (Exception e) {
                throw new RuntimeException("Incorrect server answer.");
            }
            if (registerResult.isSuccess()) {
                textReceiver.println("You have successfully registered!");
                isAuthorized = true;
                return Result.success(null);
            } else {
                return registerResult;
            }

        } catch (Exception e) {
            textReceiver.println("Error while sending register command to server, error with server connection.");
            return Result.failure(e, "Error while sending login command to server, error with server connection.");
        }
    }


    private void enterLoginData(CommandDescription registerCommandDescription) {
        textReceiver.print("Enter your login:");
        LoadDescription<String> loginDescription = loader.enterString(new LoadDescription<String>(String.class));
        textReceiver.print("Enter your password:");
        LoadDescription<String> passwordDescription = loader.enterString(new LoadDescription<String>(String.class));
        registerCommandDescription.setOneLineArguments(List.of(loginDescription, passwordDescription));
    }

    public void history() {
        history.stream()
                .limit(6)
                .forEach(textReceiver::println);
    }

    private Result<?> login() {
        enterLoginData(loginCommandDescription);
        try {
            objectSender.sendObject(loginCommandDescription);
            Result<?> loginResult = loadCommandDescriptionMap();
            if (loginResult.isSuccess()) {
                textReceiver.println("You have successfully logged!");
                isAuthorized = true;
                return Result.success(null);
            } else {
                return loginResult;
            }
        } catch (Exception e) {
            return Result.failure(e, "Error while sending login command to server, error with server connection.");
        }
    }

    public Result<Void> exit() {
        try {
            requestHandler.closeChannel();
        } catch (IOException e) {
            textReceiver.println("Error while closing channel.");
        }
        isAuthorized = false;
        System.exit(0);
        return Result.success(null);
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

    public boolean isSpecial(String name) {
        return specialCommands.containsKey(name);
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }
}
