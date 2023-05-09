package commands;


import receivers.Receiver;
import result.Result;


/**
 * Class RemoveByIdCommand for removing an item from the collection by its id.
 */
public class RemoveByIdCommand extends Command {

    /**
     * Constructor for creating a command object.
     */

    public RemoveByIdCommand() {
        super("remove_by_id {id} : remove an item from the collection by its id");
    }

    /**
     * Method execute calls the removeById() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return result of executing the command (the result of the removeById() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.removeById();
    }
}