package commands;

import common.MusicBand;
import receivers.MusicReceiver;
import receivers.Receiver;
import result.Result;

/**
 * Class ShowCommand for showing all elements of the collection.
 */
public class ShowCommand extends Command<MusicReceiver> {

    public ShowCommand() {
        super(MusicReceiver.GetInstance());
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