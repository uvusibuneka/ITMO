/**

 Invoker - a class that manages the execution of commands.
 Stores a list of all available commands and their names, as well as a history of executed commands and a stack of executed scripts.
 When executeCommand is called, it invokes the corresponding command.
 */
package managers;
import receivers.*;
import result.Result;
import commands.*;

import java.util.*;

public class Invoker {

    private Map<String, Command> commands;
    private Deque<String> history;
    private Stack<String> scriptStack;

    private Loader loader;

    /**
     * Constructor for the Invoker class.
     * Creates object of all available commands and registers them in the command list.
     *
     * @param loader - an instance of the Loader class for loading the collection from a file.
     */
    public Invoker(Loader loader) {
        this.loader = loader;
        commands = new HashMap<>();
        history = new LinkedList<>();
        scriptStack = new Stack<>();
        register("add", new AddCommand());
        register("info", new InfoCommand());
        register("show",  new ShowCommand());
        register("clear", new ClearCommand());
        register("help", new HelpCommand(commands));
        register("update", new UpdateCommand());
        register("remove_by_id", new RemoveByIdCommand());
        register("execute_script",  new ExecuteScriptCommand(this));
        register("exit",  new ExitCommand());
        register("add_if_max",  new AddIfMaxCommand());
        register("remove_greater",  new RemoveGreaterCommand());
        register("history",  new HistoryCommand(history));
        register("max_by_best_album",  new MaxByBestAlbumCommand());
        register("count_by_best_album",   new CountByBestAlbum());
        register("filter_by_best_album",  new FilterByBestAlbum());
        register("save",  new SaveCommand());
    }

    /**
     * Registers a new command in the list of available commands.
     *
     * @param name    - the name of the command.
     * @param command - an instance of the Command class that implements this command.
     */
    public void register(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Executes a command with the given name.
     *
     * @param name     - the name of the command.
     * @param receiver - an instance of the ConsoleReceiver class that executes the command.
     * @return an instance of the Result class containing information about the result of executing the command.
     */
    public Result<Void> executeCommand(String name, String[] parts, Receiver receiver) {
        try {
            if(name == null){
                return Result.failure(new Exception("Command is not found"), "Input Stream is closed. Run program to continue work.\nExiting...");
            }
            if (commands.containsKey(name)) {
                Command command = commands.get(name);
                if (command.getArgs() != parts.length - 1 && command.getArgs() != -1) {
                    return Result.failure(new Exception("Wrong number of arguments"), "Wrong number of arguments");
                }

                if(command.getArgs() == -1){
                    if(parts.length == 0){
                        return Result.failure(new Exception("Wrong number of arguments"), "Wrong number of arguments");
                    }
                    for(int i = 2; i < parts.length; i++){
                        parts[1] += " " + parts[i];
                    }
                }
                Result<Void> executeResult = command.execute(receiver, parts);
                try {
                    if (!executeResult.isSuccess()) {
                        return Result.failure(executeResult.getError().get(), executeResult.getMessage());
                    }
                } catch (Exception e) {
                    return Result.failure(e, "Error while executing command");
                }
                history.offerLast(name); // Add the command name to the history of executed commands
                if (history.size() > 6) {
                    history.pollFirst(); // If the number of commands in the history exceeds 6, remove the first command from the history
                }
                return Result.success(null);
            } else {
                return Result.failure(new Exception("Command not found"), "Command not found");
            }
        } catch (Exception e) {
            return Result.failure(e, "Error while executing command");
        }
    }

/**
 * Method for printing the script stack.
 */

    /**

     Метод для добавления имени файла скрипта в стек выполненных скриптов.
     Если имя файла уже есть в стеке, то метод вернет false, иначе - true.
     @param filename имя файла скрипта
     @return true, если имя файла успешно добавлено в стек, иначе - false
     */
    public boolean addExecutedScript(String filename) {
        if (scriptStack.contains(filename))
            return false;
        else {
            scriptStack.push(filename);
            return true;
        }
    }

    /**
     Method for removing the last executed script from the script stack.
     */
    public void removeExecutedScript() {
        if (!scriptStack.empty()) {
            scriptStack.pop();
        }
    }

    /**
     * Method for getting the size of the script stack.
     * @return
     */
    public long getScriptStackSize(){
        return scriptStack.size();
    }

    /**
     * Method for setting the Loader object.
     * @param loader object of the Loader class.
     */

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    /**
     * Method for getting the Loader object.
     * @return object of the Loader class.
     */
    public Loader getLoader() {
        return loader;
    }
}