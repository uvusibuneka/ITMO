/**

 A class that implements interactive mode for working with a collection of music groups.
 */
package managers;

import result.Result;

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
