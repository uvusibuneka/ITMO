package descriptions;

import builders.AlbumBuilder;
import common.Album;

import java.util.Arrays;
import java.util.function.Function;

public class AlbumDescription extends LoadDescription<Album> {
    {
        fields = Arrays.asList(
                new LoadDescription<String>("Name", (new AlbumBuilder())::setName, null, String.class),
                new LoadDescription<Long>("Length of Album", (new AlbumBuilder())::setLength, null, Long.class),
                new LoadDescription<Long>("Number of tracks", (new AlbumBuilder())::setTracks, null, Long.class),
                new LoadDescription<Float>("Sales", (new AlbumBuilder())::setSales, null, Float.class));
    }

    public AlbumDescription(Function<Album, Object> fieldSetter) {
        super("The best album of Music Band", fieldSetter, new AlbumBuilder(), Album.class);
    }

    public AlbumDescription() {
            super("The best album of Music Band", null, new AlbumBuilder(), Album.class);
    }
}


