package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class RemoveGreaterCommand for removing all elements greater than the specified one.
 */
public class RemoveGreaterCommand extends Command {

    /**
     * Constructor for creating a command object.
     */
    public RemoveGreaterCommand() {
        super("remove_greater {element} : remove from the collection all elements greater than the specified one", 0);
    }

    /**
     * Method execute calls the removeGreater() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return result of executing the command (the result of the removeGreater() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.removeGreater();
    }
}