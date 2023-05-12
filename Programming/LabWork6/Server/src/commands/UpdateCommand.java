package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class UpdateCommand for updating the value of the collection element whose id is equal to the specified one.
 */

public class UpdateCommand extends Command {

    /**
     * Constructor for creating a command object.
     */

    public UpdateCommand() {
        super("update id : update the value of the collection element whose id is equal to the specified one", 1);
    }

    /**
     * Method execute calls the updateById() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return result of executing the command (the result of the updateById() method of the receiver object)
     */
    public Result<Void> execute(Receiver receiver, String args[]) {
       return receiver.updateById(args);
    }
}