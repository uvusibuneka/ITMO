package сommands;
import Result.Result;
import managers.Receiver;

public class ExitCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.exit();
    }
}