package commands;

import common.MusicBand;
import receivers.Receiver;
import result.Result;

/**
 * Class UpdateCommand for updating the value of the collection element whose id is equal to the specified one.
 */

public class UpdateCommand extends Command {
    long id;
    MusicBand obj;

    /**
     * Constructor for creating a command object.
     */
    public UpdateCommand(MusicBand obj) {
        super("update id {element}: update the value of the collection element whose id is equal to the specified one");
        this.obj = obj;
    }

    /**
     * Method execute calls the updateById() method of the receiver object.
     * @return result of executing the command (the result of the updateById() method of the receiver object)
     */
    public Result<Void> execute() {
       return receiver.updateById(id, obj);
    }
}