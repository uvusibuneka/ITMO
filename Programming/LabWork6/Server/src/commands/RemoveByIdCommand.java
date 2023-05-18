package commands;

import result.Result;

/**
 * Class RemoveByIdCommand for removing an item from the collection by its id.
 */
public class RemoveByIdCommand extends Command {
    long id;

    /**
     * Constructor for creating a command object.
     */
    public RemoveByIdCommand(long id) {
        super("remove_by_id {id} : remove an item from the collection by its id");
        this.id = id;
    }

    /**
     * Method execute calls the removeById() method of the receiver object.
     * @return result of executing the command (the result of the removeById() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.removeById(id);
    }
}