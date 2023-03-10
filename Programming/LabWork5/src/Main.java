/**

 Главный класс приложения, запускающий интерактивный режим работы с коллекцией музыкальных групп.
 */

import Result.Result;
import managers.Invoker;
import managers.Loader;
import managers.Receiver;
import music.Collection;
import music.MusicBand;

import java.io.*;

public class Main {
    private static Collection<MusicBand> collection = new Collection<MusicBand>();
    private static Invoker commandManager;

    public static void main(String[] args) {
            String fileName = System.getenv("FILE_NAME");
            if (fileName == null) {
                System.out.println("Please set the absolute path to the file in the FILE_NAME environment variable.");
                return;
            }
            Loader loader = new Loader(new BufferedReader(new InputStreamReader(System.in)));

            Result<Void> resultLoad = loader.load(collection, fileName);

            if(!resultLoad.isSuccess()) {
                System.out.println("Collection is not loaded: " + resultLoad.getMessage() + ": " + resultLoad.getError().get());
                System.exit(0);
            }
            System.out.println("Collection is successfully loaded");
            Invoker commandManager = new Invoker(loader);
            runInteractiveMode(commandManager, loader);

    }

    private static void runInteractiveMode(Invoker commandManager, Loader loader) {
        Receiver receiver = new Receiver(collection, loader);

        while (true) {
            System.out.print("> ");
            String input = null;
            Result<String> readLineResult = null;
                readLineResult = loader.readLine();
                if(readLineResult.isSuccess()){
                    input = readLineResult.getValue().get();
                }else{
                    System.out.println(readLineResult.getMessage() + ": " + readLineResult.getError().get());
                }

            String[] parts = input.split(" ");
            String commandName = parts[0];
            if(parts.length != 1) {
                System.out.println("Enter just one command only without arguments:");
                continue;
            }
            Result<Void> result = commandManager.executeCommand(commandName, receiver);
            System.out.println(result.isSuccess() ? result.getMessage() : result.getMessage() + ": " + result.getError().get());
        }
    }


}
