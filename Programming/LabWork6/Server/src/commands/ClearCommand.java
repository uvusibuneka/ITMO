package commands;

import receivers.*;
import result.Result;

/**

 Class ClearCommand for clearing the collection.
 */
public class ClearCommand extends Command {

    /**
     * Constructor for creating a command object.
     */
    public ClearCommand() {
        super("clear : clear the collection", 0);
    }

    /**

     Method for executing the command.
     @param receiver manager for executing the command
     @return result for executing the command (the result of the clear() method of the receiver object)
     */
    @Override
    public Result<Void> execute(Receiver receiver, String[] args) {
        return receiver.clear();
    }
}