package commands;

import common.Album;
import common.MusicBand;
import receivers.Receiver;
import result.Result;

import java.util.TreeSet;

/**
 * Class FilterByBestAlbum for filtering the collection by the bestAlbum field.
 */
public class FilterByBestAlbum extends Command {
    Album album;

    /**
     * Constructor for creating a command object.
     * @param album bestAlbum
     */
    public FilterByBestAlbum(Album album) {
        super("filter_by_best_album {album} : print elements whose bestAlbum field value is equivalent to the specified one");
        this.album = album;
    }

    /**
     * Method execute calls the filterByBestAlbum() method of the receiver object.
     * @return result of executing the command (the result of the filterByBestAlbum() method of the receiver object)
     */
    @Override
    public Result<TreeSet<MusicBand>> execute() {
        return receiver.filterByBestAlbum(album);
    }
}