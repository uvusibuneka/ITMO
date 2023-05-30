package common.descriptions;

import common.builders.AlbumBuilder;
import common.builders.CoordinatesBuilder;
import common.builders.MusicBandBuilder;
import common.Album;
import common.Coordinates;
import common.MusicBand;
import common.MusicGenre;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Function;

public class MusicBandDescription extends LoadDescription<MusicBand> implements Serializable {
    {
        fields = Arrays.asList(new LoadDescription<>("Name of Music Band", (new MusicBandBuilder())::setName, null, String.class),
                new LoadDescription<>("Coordinates", (new MusicBandBuilder())::setCoordinates, new CoordinatesBuilder(), Coordinates.class),
                new LoadDescription<>("Creation Date", (new MusicBandBuilder())::setCreationDate, null, LocalDate.class),
                new LoadDescription<>("Number of participants", (new MusicBandBuilder())::setNumberOfParticipants, null, Long.class),
                new LoadDescription<>("Best Album", (new MusicBandBuilder())::setBestAlbum, new AlbumBuilder(), Album.class),
                new LoadDescription<>("Genre", (new MusicBandBuilder())::setGenre, null, MusicGenre.class));
    }

    public MusicBandDescription(Function<MusicBand, Object> fieldSetter) {
        super("Music Band", fieldSetter, new MusicBandBuilder(), MusicBand.class);
    }

    public MusicBandDescription() {
        super("Music Band", null, new MusicBandBuilder(), MusicBand.class);
    }

}
