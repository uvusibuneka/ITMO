package descriptions;

import builders.AlbumBuilder;
import common.Album;
import managers.LoadDescription;

import java.util.Arrays;
import java.util.function.Function;

public class AlbumDescription extends LoadDescription<Album> {
    {
        fields = Arrays.asList(
                new LoadDescription<String>("Name", (new AlbumBuilder())::setName, null, String.class),
                new LoadDescription<Long>("Length of Album", (new AlbumBuilder())::setLength, null, Integer.class),
                new LoadDescription<Long>("Number of tracks", (new AlbumBuilder())::setTracks, null, String.class),
                new LoadDescription<Float>("Sales", (new AlbumBuilder())::setSales, null, String.class));
    }

    public AlbumDescription(Function<Album, Object> fieldSetter) {
        super("Album", fieldSetter, new AlbumBuilder(), Album.class);
    }

    public AlbumDescription() {
        super("Album", null, new AlbumBuilder(), Album.class);
    }
}
