package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class SaveCommand for saving the collection to a file.
 */
public class SaveCommand extends Command {

    /**
     * Constructor for creating a command object.
     */
    public SaveCommand() {
        super("save : save the collection to a file", 0);
    }

    /**
     * Method execute calls the saveCollection() method of the receiver object.
     * @param receiver receiver for executing the command
     * @return result of executing the command (the result of the saveCollection() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.saveCollection();
    }
}