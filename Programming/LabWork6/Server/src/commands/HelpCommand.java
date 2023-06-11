package commands;

import common.Album;
import common.descriptions.AlbumDescription;
import common.descriptions.CommandDescription;
import common.MusicBand;
import common.descriptions.LoadDescription;
import common.descriptions.MusicBandDescription;
import receivers.MusicReceiver;
import result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpCommand extends Command<MusicReceiver> {
    HashMap<String, CommandDescription> commands;

    public HelpCommand() throws Exception{
        super(MusicReceiver.GetInstance());
        commands = new HashMap<>();
        ArrayList<LoadDescription<?>> inline_arguments = new ArrayList<>();
        ArrayList<LoadDescription<?>> object_arguments = new ArrayList<>();

        commands.put("info", new CommandDescription("info", "Получить информацию о коллекции"));
        commands.put("show", new CommandDescription("show", "Получить элементы коллекции"));
        commands.put("clear", new CommandDescription("clear", "Очистить коллекцию"));
        commands.put("help", new CommandDescription("help", "Получить справочную информацию"));
        commands.put("exit", new CommandDescription("exit", "Выйти из приложения"));
        commands.put("history", new CommandDescription("history", "История введенных команд"));
        commands.put("max_by_best_album", new CommandDescription("max_by_best_album", "Получить MusicBand за наилучшим Album"));

        commands.put("add", new CommandDescription("add", "Добавить элемент в коллекцию", null, List.of(new MusicBandDescription())));
        commands.put("add_if_max", new CommandDescription("add_if_max", "Добавить элемент в коллекцию, проверив что больше уже имеющихся", null, List.of(new MusicBandDescription())));
        commands.put("remove_greater", new CommandDescription("remove_greater", "Удалить элемент из коллекции", null, List.of(new MusicBandDescription())));

        commands.put("update", new CommandDescription("update", "Обновить элемент коллекции с указанным id", List.of(new LoadDescription<>(Integer.class)), List.of(new MusicBandDescription())));

        commands.put("remove_by_id", new CommandDescription("remove_by_id", "Удалить элемент с указанным id из коллекции",List.of(new LoadDescription<>(Integer.class)), null));

        commands.put("execute_script", new CommandDescription("execute_script", "Исполнить скрипт", List.of(new LoadDescription<>(String.class)), null));

        commands.put("count_by_best_album", new CommandDescription("count_by_best_album", "Получить количество элементов, лучший Album которых соответствует заданному", null, List.of(new AlbumDescription())));
    }
    @Override
    public Result<HashMap<String, CommandDescription>> execute() {
        return Result.success(commands);
    }
}
