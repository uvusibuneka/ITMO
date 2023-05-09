/**

 A class that implements interactive mode for working with a collection of music groups.
 */
package managers;

import receivers.ConsoleReceiver;
import result.Result;

import java.io.File;

public class InteractiveMode {

    private Loader loader;
    private Invoker commandManager;
    private ConsoleReceiver receiver;

    /**
     * Class constructor.
     * @param loader Collection loader.
     * @param commandManager Command manager.
     * @param receiver Object that implements interactive mode for working with the collection.
     */
    public InteractiveMode(Loader loader, Invoker commandManager, ConsoleReceiver receiver) {
        this.loader = loader;
        this.commandManager = commandManager;
        this.receiver = receiver;
        // Getting the file name from an environment variable
        String fileName = System.getenv("FILE_NAME");
        receiver.setFileName(fileName);
        if (fileName == null) {
            System.out.println("Environment variable FILE_NAME is not set");
            System.exit(0);
        }

        File file = new File(fileName);

        if(!file.exists()) {
            System.out.println("File " + fileName + " does not exist");
            return;
        }

        if(!file.canRead()) {
            System.out.println("File " + fileName + " is not readable");
            return;
        }

        Result<Void> resultLoad = loader.load(receiver.getCollection(), fileName);

        if(!resultLoad.isSuccess()) {
            System.out.println("Collection is not loaded: " + resultLoad.getMessage());
            System.exit(0);
        }
        System.out.println("Collection is loaded");
    }


    /**
     * Method for starting interactive mode for working with the collection of music groups.
     */
    public void start() {
        while (true) {
            System.out.print("> ");
            String input = null;
            Result<String> readLineResult = loader.readLine();
            if(readLineResult.isSuccess()){
                input = readLineResult.getValue().get();
            }else{
                System.out.println("Error: input stream is closed");
                System.exit(0);
            }
            String[] parts = input.split(" ");
            String commandName = parts[0];

            Result<Void> result = commandManager.executeCommand(commandName, parts, receiver);
            System.out.println(result.getMessage());
        }
    }
}
