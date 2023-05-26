package managers;

import builders.AlbumBuilder;
import builders.Buildable;
import builders.CoordinatesBuilder;
import builders.MusicBandBuilder;
import common.Album;
import common.Coordinates;
import common.MusicGenre;
import descriptions.LoadDescription;

import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Function;

public class UserDescription extends LoadDescription<User> {
    {
        fields = Arrays.asList(new LoadDescription<Long>("ID", (new UserBuilder())::setID, null, Long.class),
                new LoadDescription<String>("login", (new UserBuilder())::setLogin, null, String.class),
                new LoadDescription<String>("password", (new UserBuilder())::setPassword, null, String.class));
    }
    public UserDescription(String description, Function<User, ?> fieldSetter, Buildable<User> builder, Class<User> type) {
        super(description, fieldSetter, builder, type);
    }
}
