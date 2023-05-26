package managers.user;

import common.descriptions.LoadDescription;

import java.util.Arrays;
import java.util.function.Function;

public class UserDescription extends LoadDescription<User> {
    {
        fields = Arrays.asList(new LoadDescription<Long>("ID", (new UserBuilder())::setID, null, Long.class),
                new LoadDescription<String>("login", (new UserBuilder())::setLogin, null, String.class),
                new LoadDescription<String>("password", (new UserBuilder())::setPassword, null, String.class));
    }

    public UserDescription(Function<User, Object> fieldSetter) {
        super("Music Band", fieldSetter, new UserBuilder(), User.class);
    }

    public UserDescription() {
        super("Music Band", null, new UserBuilder(), User.class);
    }
}
