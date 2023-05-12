package commands;

import receivers.*;
import result.Result;

/**
 * Class CountByBestAlbum for counting the number of elements whose bestAlbum field value is equal to the specified one.
 */
public class CountByBestAlbum extends Command {

    /**
     * Constructor for creating a command object.
     */

    public CountByBestAlbum() {
        super("count_by_best_album : print the number of elements whose bestAlbum field value is equal to the specified one", 0);
    }

    /**
     * Method execute calls the countByBestAlbum() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return the result of executing the command (the result of the countByBestAlbum() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.countByBestAlbum();
    }
}
