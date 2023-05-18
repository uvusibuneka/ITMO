package commands;

import common.Album;
import receivers.*;
import result.Result;

/**
 * Class CountByBestAlbum for counting the number of elements whose bestAlbum field value is equal to the specified one.
 */
public class CountByBestAlbum extends Command {
    Album album;

    /**
     * Constructor for creating a command object.
     * @param album
     */
    public CountByBestAlbum(Album album) {
        super("count_by_best_album : print the number of elements whose bestAlbum field value is equal to the specified one");
        this.album = album;
    }

    /**
     * Method execute calls the countByBestAlbum() method of the receiver object.
     * @return the result of executing the command (the result of the countByBestAlbum() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.countByBestAlbum(album);
    }
}
