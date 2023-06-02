package managers.user;

import common.descriptions.LoadDescription;

import java.util.Arrays;
import java.util.function.Function;

public class UserDescription extends LoadDescription<User> {

    public UserDescription(UserBuilder ub) {
        super("User", null, ub, User.class);
        fields = Arrays.asList(new LoadDescription<Long>("ID", ub::setID, null, Long.class),
                new LoadDescription<String>("login", ub::setLogin, null, String.class),
                new LoadDescription<String>("password", ub::setPassword, null, String.class));
    }
}
