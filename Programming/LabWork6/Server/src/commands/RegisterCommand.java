package commands;

import managers.user.User;
import receivers.UserReceiver;
import result.Result;

public class RegisterCommand extends Command<UserReceiver> {
    User user;
    public RegisterCommand(User user) {
        super(UserReceiver.GetInstance());
        this.user = user;
    }

    @Override
    public Result<?> execute() {
        return receiver.register(user);
    }
}
