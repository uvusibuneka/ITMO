package commands;

import receivers.*;
import result.Result;

/**

 Class AddIfMaxCommand for adding a new element to the collection if it's greater than the maximum element.

 */
public class AddIfMaxCommand extends Command {

    /**
     * Constructor for creating a command object.
     */

    public AddIfMaxCommand() {
        super("add_if_max {element} : add a new element to the collection if it's greater than the maximum element", 0);
    }

    /**

     Method execute calls the addIfMax() method of the receiver object.
     @param receiver manager for executing the command
     @return result of executing the command (the result of the addIfMax() method of the receiver object)
     */
    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.addIfMax();
    }
}