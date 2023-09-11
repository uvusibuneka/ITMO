package receivers;

import managers.file.*;
import common.Collection;
import managers.file.decorators.DataBase.DBReader;
import managers.file.decorators.DataBase.DBWriter;
import managers.user.User;
import managers.user.UserBuilder;
import managers.user.UserDescription;
import result.Result;

public class UserReceiver extends Receiver<User>{
    private static UserReceiver instance;

    public static UserReceiver GetInstance() throws Exception{
        if (instance == null) {
            instance = new UserReceiver();
        }
        return instance;
    }

    private UserReceiver() throws Exception{
        try {
            Collection<User> tmp = new Collection<>();
            collection_to_file_writer = new AbstractWriter<>("Users") {
                @Override
                public void write() throws Exception {

                }

                @Override
                public Result<Boolean> insert(User user) {
                    return null;
                }

                @Override
                public Result<Boolean> update(User user, long i) {
                    return null;
                }

                @Override
                public Result<Boolean> remove(long i) {
                    return null;
                }

                @Override
                public Result<Boolean> remove(String s, String s1) {
                    return null;
                }
            };
            AbstractReader<User> Collection_from_file_loader = new AbstractReader<>("Users", new UserDescription(new UserBuilder()), tmp) {
                @Override
                public Result<Collection<User>> read() {
                    return null;
                }
            };

            collection_to_file_writer = new DBWriter<>("Users", collection_to_file_writer, new UserDescription(new UserBuilder()));
            Collection_from_file_loader = new DBReader<>("Users", new UserDescription(new UserBuilder()), Collection_from_file_loader, tmp);

            collection = new common.Collection<>(Collection_from_file_loader, collection_to_file_writer);
        } catch (NullPointerException e){
            throw e;
        }
    }
    public Result<Boolean> check_login(String login, String password){
        return Result.success(
                collection.
                getCollection().
                stream().
                anyMatch((User user) -> (user.getLogin().equals(login) && user.getHashedPassword(password).equals(user.getPassword()))));
    }

    public Result<Void> register(User user){
        if (collection.
                getCollection().
                stream().
                noneMatch((User u) -> (u.getLogin().equals(user.getLogin())))){
            return this.add(user);
        } else{
            return Result.failure(new Exception("Логин занят"), "Логин занят");
        }
    }

    @Override
    public Result<Collection<User>> showElementsOfCollection() {
        return Result.success(collection);
    }
}
