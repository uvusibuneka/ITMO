package receivers;

import managers.file.Abstract_file_reader;
import managers.file.Abstract_file_writer;
import managers.file.File_reader;
import managers.file.File_writer;
import managers.file.decorators.CSV.CSV_reader;
import managers.file.decorators.CSV.CSV_writer;
import common.Collection;
import managers.user.User;
import managers.user.UserDescription;
import result.Result;

import java.io.FileNotFoundException;

public class UserReceiver extends Receiver<User>{
    private static UserReceiver instance;

    public static UserReceiver GetInstance() throws Exception{
        if (instance == null) {
            instance = new UserReceiver();
        }
        return instance;
    }

    private UserReceiver() throws Exception{

        String fileName = System.getenv("USERS_FILE");
        try {
            Collection<User> tmp = new Collection<>();
            Abstract_file_writer<User> Collection_to_file_writer = new File_writer<>(fileName);
            Abstract_file_reader<User> Collection_from_file_loader = new File_reader<>(fileName, new UserDescription(), tmp);

            Collection_to_file_writer = new CSV_writer<>(fileName, Collection_to_file_writer);
            Collection_from_file_loader = new CSV_reader<>(fileName, new UserDescription(), Collection_from_file_loader, tmp);

            collection = new common.Collection<>(Collection_from_file_loader, Collection_to_file_writer);
        } catch (NullPointerException e){
            throw new NullPointerException("USERS_FILE is not set");
        }
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
                noneMatch((User u) -> (u.getLogin().equals(user.getLogin())))){
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
