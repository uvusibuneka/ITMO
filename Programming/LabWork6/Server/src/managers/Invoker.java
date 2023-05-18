/**

 Invoker - a class that manages the execution of commands.
 Stores a list of all available commands and their names, as well as a history of executed commands and a stack of executed scripts.
 When executeCommand is called, it invokes the corresponding command.
 */
package managers;
import common.CommandDescription;
import receivers.*;
import result.Result;
import commands.*;

import java.util.*;
import java.util.function.Function;

public class Invoker {

    private Map<String, Function<CommandDescription, Command>> command_creators;
    private Deque<String> history;

    /**
     * Constructor for the Invoker class.
     * Creates object of all available commands and registers them in the command list.
     */
    public Invoker() {
        history = new LinkedList<>();
        command_creators = new HashMap<>();
        command_creators.put("add", this::add);
        command_creators.put("info", this::info);
        command_creators.put("show",  this::show);
        command_creators.put("clear", this::clear);
        command_creators.put("help", this::help);
        command_creators.put("update", this::update);
        command_creators.put("remove_by_id", this::remove_by_id);
        command_creators.put("exit", this::exit);
        command_creators.put("add_if_max", this::add_if_max);
        command_creators.put("remove_greater", this::remove_greater);
        command_creators.put("history", this::history);
        command_creators.put("max_by_best_album", this::max_by_best_album);
        command_creators.put("count_by_best_album", this::count_by_best_album);
        command_creators.put("filter_by_best_album", this::filter_by_best_album);
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

    public Command add(CommandDescription cd){
        return new AddCommand();
    }
    public Command info(CommandDescription cd){
        return new InfoCommand();
    }
    public Command show(CommandDescription cd){
        return new ShowCommand();
    }
    public Command clear(CommandDescription cd){
        return new ClearCommand();
    }
    public Command help(CommandDescription cd){
        return new HelpCommand();
    }
    public Command update(CommandDescription cd){
        return new UpdateCommand();
    }
    public Command remove_by_id(CommandDescription cd){
        return new RemoveByIdCommand();
    }
    public Command exit(CommandDescription cd){
        return new ExitCommand();
    }
    public Command add_if_max(CommandDescription cd){
        return new AddIfMaxCommand();
    }
    public Command remove_greater(CommandDescription cd){
        return new RemoveGreaterCommand();
    }
    public Command history(CommandDescription cd){
        return new HistoryCommand();
    }
    public Command max_by_best_album(CommandDescription cd){
        return new MaxByBestAlbumCommand();
    }
    public Command count_by_best_album(CommandDescription cd){
        return new CountByBestAlbum();
    }
    public Command filter_by_best_album(CommandDescription cd){
        return new FilterByBestAlbum();
    }
}