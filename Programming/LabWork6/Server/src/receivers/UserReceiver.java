package receivers;

import common.User;
import result.Result;

public class UserReceiver extends Receiver<User>{
    @Override
    public Result<Void> add(User obj) {
        return null;
    }

    @Override
    public Result<Void> clear() {
        return null;
    }

    @Override
    public Result<String> info() {
        return null;
    }

    @Override
    public Result<Void> removeById(long id) {
        return null;
    }

    @Override
    public Result<Void> saveCollection() {
        return null;
    }

    @Override
    public Result showElementsOfCollection() {
        return null;
    }

    @Override
    public Result<Void> updateById(long id, User newElement) {
        return null;
    }
}
