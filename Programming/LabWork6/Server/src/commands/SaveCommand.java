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
        super("save : save the collection to a file");
    }

    /**
     * Method execute calls the saveCollection() method of the receiver object.
     * @return result of executing the command (the result of the saveCollection() method of the receiver object)
     */

    @Override
    public Result<Void> execute() {
        return receiver.saveCollection();
    }
}