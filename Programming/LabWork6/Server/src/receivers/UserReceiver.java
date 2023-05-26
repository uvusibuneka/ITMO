package receivers;

import common.MusicBand;
import managers.User;
import result.Result;

import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserReceiver extends Receiver<User>{
    private static UserReceiver instance;

    public static UserReceiver GetInstance() {
        if (instance == null) {
            instance = new UserReceiver();
        }
        return instance;
    }

    private UserReceiver(){

    }
    public Result<Boolean> check_login(String login, String password){
        return Result.success(
                collection.
                getCollection().
                stream().
                anyMatch((User user) -> (user.getLogin().equals(login) && user.getPassword().equals(password))));
    }

    public Result<Void> register(User user){
        if (collection.
                getCollection().
                stream().
                anyMatch((User u) -> (u.getLogin().equals(user.getLogin())))){
            return this.add(user);
        } else{
            return Result.failure(new Exception("Логин занят"), "Логин занят");
        }
    }

    @Override
    public Result<User[]> showElementsOfCollection() {
        User[] arr = new User[0];
        arr = collection.getCollection().toArray(arr);
        return Result.success(arr);
    }
}
