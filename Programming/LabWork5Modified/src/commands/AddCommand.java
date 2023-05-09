package commands;

import receivers.ConsoleReceiver;
import receivers.Receiver;
import result.Result;

/**

 Class AddCommand for adding a new element to the collection.
 */
public class AddCommand extends Command {

    private ConsoleReceiver receiver;

    /**
     * Constructor for creating a command object.
     */
    public AddCommand() {
        super("add {element} : add a new element to the collection", 0);
    }

    /**

     Method execute calls the add() method of the receiver object.
     @param receiver an object that will execute the command
     @return the result of executing the command (the result of the add() method of the receiver object)

     */
    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.add();
    }
}