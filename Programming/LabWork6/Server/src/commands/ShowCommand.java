package commands;

import common.MusicBand;
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
        super("show : show all elements of the collection");
    }

    /**
     * Method execute calls the showElementsOfCollection() method of the receiver object.
     * @return result of executing the command (the result of the showElementsOfCollection() method of the receiver object)
     */

    @Override
    public Result<MusicBand[]> execute() {
        return receiver.showElementsOfCollection();
    }
}