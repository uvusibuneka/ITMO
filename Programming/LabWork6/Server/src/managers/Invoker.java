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

    private final Map<String, Function<CommandDescription, Command<MusicReceiver>>> command_creators;
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
        command_creators.put("add_if_max", this::add_if_max);
        command_creators.put("remove_greater", this::remove_greater);
        command_creators.put("max_by_best_album", this::max_by_best_album);
        command_creators.put("count_by_best_album", this::count_by_best_album);
        command_creators.put("filter_by_best_album", this::filter_by_best_album);
    }

    /**
     * Executes a command with the given name.
     *
     * @param name     - the name of the command.
     * @param cd
     * @return an instance of the Result class containing information about the result of executing the command.
     */
    public Result<?> executeCommand(String name, CommandDescription cd) {
        Command<MusicReceiver> commandObj = null;
        if (command_creators.containsKey(name)){
            commandObj = command_creators.get(name).apply(cd);
        }
        if (commandObj != null)
            return commandObj.execute();
        else
            return Result.failure(new Exception("Команда не найдена"));
    }

    public Command<MusicReceiver> add(CommandDescription cd){
        return new AddCommand();
    }
    public Command<MusicReceiver> info(CommandDescription cd){
        return new InfoCommand();
    }
    public Command<MusicReceiver> show(CommandDescription cd){
        return new ShowCommand();
    }
    public Command<MusicReceiver> clear(CommandDescription cd){
        return new ClearCommand();
    }
    public Command<MusicReceiver> help(CommandDescription cd){
        return new HelpCommand();
    }
    public Command<MusicReceiver> update(CommandDescription cd){
        return new UpdateCommand();
    }
    public Command<MusicReceiver> remove_by_id(CommandDescription cd){
        return new RemoveByIdCommand();
    }
    public Command<MusicReceiver> add_if_max(CommandDescription cd){
        return new AddIfMaxCommand();
    }
    public Command<MusicReceiver> remove_greater(CommandDescription cd){
        return new RemoveGreaterCommand();
    }
    public Command<MusicReceiver> max_by_best_album(CommandDescription cd){
        return new MaxByBestAlbumCommand();
    }
    public Command<MusicReceiver> count_by_best_album(CommandDescription cd){
        return new CountByBestAlbum();
    }
    public Command<MusicReceiver> filter_by_best_album(CommandDescription cd){
        return new FilterByBestAlbum();
    }
}