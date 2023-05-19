package commands;

import common.MusicBand;
import receivers.MusicReceiver;
import receivers.Receiver;
import result.Result;

/**
 * Class RemoveGreaterCommand for removing all elements greater than the specified one.
 */
public class RemoveGreaterCommand extends Command<MusicReceiver>  {
    MusicBand element;

    /**
     * Constructor for creating a command object.
     */
    public RemoveGreaterCommand(MusicBand element) {
        super(MusicReceiver.GetInstance());
        this.element = element;
    }

    /**
     * Method execute calls the removeGreater() method of the receiver object.
     * @return result of executing the command (the result of the removeGreater() method of the receiver object)
     */

    @Override
    public Result<Void> execute() {
        return receiver.removeGreater(element);
    }
}