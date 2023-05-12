package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class MaxByBestAlbumCommand for displaying the element with the maximum value of the bestAlbum field.
 */
public class MaxByBestAlbumCommand extends Command {

     /**
     * Constructor for creating a command object.
     */

    public MaxByBestAlbumCommand() {
        super("max_by_best_album : выводит элемент коллекции с наибольшим значением поля bestAlbum", 0);
    }

    /**
     * Method execute calls the maxByBestAlbum() method of the receiver object.
     * @param receiver receiver for executing the command
     * @return result of executing the command (the result of the maxByBestAlbum() method of the receiver object)
     */
    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.maxByBestAlbum();
    }
}