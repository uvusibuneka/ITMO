package UserInterface;

import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import loaders.ConsoleLoader;
import managers.BaseTextReceiver;
import modules.InteractiveMode;
import modules.TextReceiver;
import result.Result;

import java.util.Map;
import java.util.function.Supplier;

public class ConsoleUI {

    private static ConsoleUI consoleUI;
    private InteractiveMode interactiveMode;

    private BaseTextReceiver textReceiver = new TextReceiver();

    private ConsoleLoader loader = new ConsoleLoader(textReceiver);

    private static Map<String, Supplier<Result<?>>> authorizationMap;
    public ConsoleUI(InteractiveMode interactiveMode) {
        this.interactiveMode = interactiveMode;
    }

    public static ConsoleUI getInstance(InteractiveMode interactiveMode){
        if(consoleUI == null)
            consoleUI = new ConsoleUI(interactiveMode);
        authorizationMap = Map.of("l", interactiveMode::login,
                "r", interactiveMode::register,
                "q", interactiveMode::exit
        );
        return consoleUI;
    }

    public void start(){
        textReceiver.println("Welcome to interactive mode! Are you want to register or login? (r/l). Type \"q\" to exit.");
        while (true) {
            String command = loader.enterWithMessage(">", new LoadDescription<>(String.class)).getValue();
            if (authorizationMap.containsKey(command)) {
                Result<?> result = authorizationMap.get(command).get();
                if(result.isSuccess() && command.equals("l")){
                    break;
                }
                textReceiver.println(result.getMessage());
            } else {
                textReceiver.println("Unknown command!");
            }
        }
        textReceiver.println("Interactive mode started! Check command help to see available commands.");
        while (true) {
            CommandDescription command;
            try {
                command = loader.parseCommand(interactiveMode,
                        loader.enterWithMessage(">", new LoadDescription<>(String.class)).getValue()
                );
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
                continue;
            }
            try {
                command.getCaller().call();
            }catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
            interactiveMode.addCommandToHistory(command.getName());
            interactiveMode.clearCommandQueue();
        }
    }
}
