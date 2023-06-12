package common.descriptions;

import common.builders.AlbumBuilder;
import common.builders.Buildable;
import common.builders.CoordinatesBuilder;
import common.builders.MusicBandBuilder;
import common.Album;
import common.Coordinates;
import common.MusicBand;
import common.MusicGenre;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class MusicBandDescription extends LoadDescription<MusicBand> implements Serializable {
    protected MusicBandBuilder builder = new MusicBandBuilder();
    {
        fields = new ArrayList<>(Arrays.asList(new LoadDescription<>("Name of Music Band", builder::setName, null, String.class),
                new CoordinatesDescription(builder::setCoordinates),
                new LoadDescription<>("Creation Date",builder::setCreationDate, null, LocalDate.class),
                new LoadDescription<>("Number of participants", builder::setNumberOfParticipants, null, Long.class),
                new AlbumDescription(builder::setBestAlbum),
                new LoadDescription<>("Genre", builder::setGenre, null, MusicGenre.class)));
    }

    public MusicBandDescription(SerialFunction<MusicBand, Object> fieldSetter) {
        super("Music Band", fieldSetter, MusicBand.class);
    }

    public MusicBandDescription() {
        super("Music Band",
                (SerialFunction<MusicBand, ?>) null,
                MusicBand.class);
    }

}
