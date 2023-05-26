package commands;

import common.Album;
import common.descriptions.CommandDescription;
import common.MusicBand;
import common.descriptions.LoadDescription;
import receivers.MusicReceiver;
import result.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpCommand extends Command<MusicReceiver> {
    HashMap<String, CommandDescription> commands;

    public HelpCommand(){
        super(MusicReceiver.GetInstance());
        commands = new HashMap<>();
        ArrayList<LoadDescription<?>> inline_arguments = new ArrayList<>();
        ArrayList<LoadDescription<?>> object_arguments = new ArrayList<>();

        commands.put("info", new CommandDescription("info"));
        commands.put("show", new CommandDescription("show"));
        commands.put("clear", new CommandDescription("clear"));
        commands.put("help", new CommandDescription("help"));
        commands.put("exit", new CommandDescription("exit"));
        commands.put("history", new CommandDescription("history"));

        object_arguments.add(new LoadDescription<>(MusicBand.class));
        commands.put("add", new CommandDescription("add", null, object_arguments));
        commands.put("add_if_max", new CommandDescription("add_if_max", null, object_arguments));
        commands.put("remove_greater", new CommandDescription("remove_greater", null, object_arguments));
        object_arguments.clear();

        inline_arguments.add(new LoadDescription<>(Integer.class));
        object_arguments.add(new LoadDescription<>(MusicBand.class));
        commands.put("update", new CommandDescription("update", inline_arguments, object_arguments));
        object_arguments.clear();
        inline_arguments.clear();

        inline_arguments.add(new LoadDescription<>(Integer.class));
        commands.put("remove_by_id", new CommandDescription("remove_by_id", inline_arguments, null));
        inline_arguments.clear();

        inline_arguments.add(new LoadDescription<>(String.class));
        commands.put("execute_script", new CommandDescription("execute_script", inline_arguments, null));
        inline_arguments.clear();

        object_arguments.add(new LoadDescription<>(Album.class));
        commands.put("max_by_best_album", new CommandDescription("max_by_best_album", null, object_arguments));
        commands.put("count_by_best_album", new CommandDescription("count_by_best_album", null, object_arguments));
        object_arguments.clear();
    }
    @Override
    public Result<HashMap<String, CommandDescription>> execute() {
        return Result.success(commands);
    }
}
