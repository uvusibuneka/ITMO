package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class FilterByBestAlbum for filtering the collection by the bestAlbum field.
 */
public class FilterByBestAlbum extends Command {

    /**
     * Constructor for creating a command object.
     */

    public FilterByBestAlbum() {
        super("filter_by_best_album {bestAlbum} : print elements whose bestAlbum field value is equivalent to the specified one", 0);
    }

    /**
     * Method execute calls the filterByBestAlbum() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return result of executing the command (the result of the filterByBestAlbum() method of the receiver object)
     */
    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.filterByBestAlbum();
    }
}