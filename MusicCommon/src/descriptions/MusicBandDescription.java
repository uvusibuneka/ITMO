package descriptions;

import builders.AlbumBuilder;
import builders.CoordinatesBuilder;
import builders.MusicBandBuilder;
import common.Album;
import common.Coordinates;
import common.MusicBand;
import common.MusicGenre;
import managers.LoadDescription;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Function;

public class MusicBandDescription extends LoadDescription<MusicBand> {
    {
        fields = Arrays.asList(new LoadDescription<String>("Name of Music Band", (new MusicBandBuilder())::setName, null, String.class),
                new LoadDescription<Coordinates>("Coordinates", (new MusicBandBuilder())::setCoordinates, new CoordinatesBuilder(), Coordinates.class),
                new LoadDescription<LocalDate>("Creation Date", (new MusicBandBuilder())::setCreationDate, null, String.class),
                new LoadDescription<Long>("Number of participants", (new MusicBandBuilder())::setNumberOfParticipants, null, String.class),
                new LoadDescription<Album>("Best Album", (new MusicBandBuilder())::setBestAlbum, new AlbumBuilder(), Album.class),
                new LoadDescription<MusicGenre>("Genre", (new MusicBandBuilder())::setGenre, null, MusicGenre.class));
    }

    public MusicBandDescription(Function<MusicBand, Object> fieldSetter) {
        super("Music Band", fieldSetter, new MusicBandBuilder(), MusicBand.class);
    }

    public MusicBandDescription() {
        super("Music Band", null, new MusicBandBuilder(), MusicBand.class);
    }

}
