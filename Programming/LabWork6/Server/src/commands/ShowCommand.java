package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class ShowCommand for showing all elements of the collection.
 */
public class ShowCommand extends Command {

    /**
     * Constructor for creating a command object.
     */

    public ShowCommand() {
        super("show : show all elements of the collection", 0);
    }

    /**
     * Method execute calls the showElementsOfCollection() method of the receiver object.
     * @param receiver receiver for executing the command
     * @return result of executing the command (the result of the showElementsOfCollection() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.showElementsOfCollection();
    }
}