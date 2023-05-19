package commands;

import common.Album;
import common.CommandDescription;
import common.MusicBand;
import receivers.MusicReceiver;
import result.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpCommand extends Command<MusicReceiver> {
    HashMap<String, CommandDescription> commands;

    public HelpCommand(){
        super(MusicReceiver.GetInstance());
        commands = new HashMap<>();
        ArrayList<String> inline_arguments = new ArrayList<>();
        ArrayList<Object> object_arguments = new ArrayList<>();

        commands.put("info", new CommandDescription("info", inline_arguments, object_arguments));
        commands.put("show", new CommandDescription("show", inline_arguments, object_arguments));
        commands.put("clear", new CommandDescription("clear", inline_arguments, object_arguments));
        commands.put("help", new CommandDescription("help", inline_arguments, object_arguments));
        commands.put("exit", new CommandDescription("exit", inline_arguments, object_arguments));
        commands.put("history", new CommandDescription("history", inline_arguments, object_arguments));

        object_arguments.add(MusicBand.class);
        commands.put("add", new CommandDescription("add", inline_arguments, object_arguments));
        commands.put("add_if_max", new CommandDescription("add_if_max", inline_arguments, object_arguments));
        commands.put("remove_greater", new CommandDescription("remove_greater", inline_arguments, object_arguments));
        object_arguments.clear();

        inline_arguments.add("id");
        object_arguments.add(MusicBand.class);
        commands.put("update", new CommandDescription("update", inline_arguments, object_arguments));
        object_arguments.clear();
        inline_arguments.clear();

        inline_arguments.add("id");
        commands.put("remove_by_id", new CommandDescription("remove_by_id", inline_arguments, object_arguments));
        inline_arguments.clear();

        inline_arguments.add("file path");
        commands.put("execute_script", new CommandDescription("execute_script", inline_arguments, object_arguments));
        inline_arguments.clear();

        object_arguments.add(Album.class);
        commands.put("max_by_best_album", new CommandDescription("max_by_best_album", inline_arguments, object_arguments));
        commands.put("count_by_best_album", new CommandDescription("count_by_best_album", inline_arguments, object_arguments));
        object_arguments.clear();
    }
    @Override
    public Result<HashMap<String, CommandDescription>> execute() {
        return Result.success(commands);
    }
}
