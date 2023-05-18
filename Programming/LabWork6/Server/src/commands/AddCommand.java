package commands;

import common.MusicBand;
import receivers.ConsoleReceiver;
import receivers.Receiver;
import result.Result;

/**
 * Class AddCommand for adding a new element to the collection.
 */
public class AddCommand extends Command {
    MusicBand element;

    /**
     * Constructor for creating a command object.
     * @param element
     */
    public AddCommand(MusicBand element) {
        super("add {element} : add a new element to the collection");
        this.element = element;
    }

    /**
     * Method execute calls the add() method of the receiver object.
     * @return the result of executing the command (the result of the add() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.add(element);
    }
}