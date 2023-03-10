package сommands;

import Result.Result;
import managers.Receiver;

public class InfoCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.info();
    }
}