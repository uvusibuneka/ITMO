package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class InfoCommand for displaying information about the collection.
 */
public class InfoCommand extends Command {

    /**
     * Constructor for creating a command object.
     */
    public InfoCommand() {
        super("info : display information about the collection (type, initialization date, number of elements, etc.)");
    }

    /**
     * Method execute calls the info() method of the receiver object.
     * @return result of executing the command (the result of the info() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.info();
    }
}