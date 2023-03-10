package сommands;

import Result.Result;
import managers.Receiver;

public class ClearCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.clear();
    }
}
