package commands;

import common.MusicBand;
import receivers.*;
import result.Result;

/**
 * Class AddIfMaxCommand for adding a new element to the collection if it's greater than the maximum element.
 */
public class AddIfMaxCommand extends Command {

    MusicBand element;

    /**
     * Constructor for creating a command object.
     * @param element
     */
    public AddIfMaxCommand(MusicBand element) {
        super("add_if_max {element} : add a new element to the collection");
        this.element = element;
    }

    /**
     * Method execute calls the addIfMax() method of the receiver object.
     * @return the result of executing the command (the result of the add() method of the receiver object)
     */
    @Override
    public Result<String> execute() {
        return receiver.addIfMax(element);
    }
}