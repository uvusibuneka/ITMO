/**

 Главный класс приложения, запускающий интерактивный режим работы с коллекцией музыкальных групп.
 */

import common.Collection;
import common.MusicBand;
import managers.InteractiveMode;
import managers.Invoker;
import managers.Loader;
import receivers.ConsoleReceiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        // Создание менеджера команд и запуск интерактивного режима
        Collection<MusicBand> collection = new Collection<MusicBand>();
        Loader loader = new Loader(new BufferedReader(new InputStreamReader(System.in)), true);
        Invoker commandManager = new Invoker(loader);
        InteractiveMode interactiveMode = new InteractiveMode(loader, commandManager, new ConsoleReceiver(collection, loader));
        interactiveMode.start();
    }
}
