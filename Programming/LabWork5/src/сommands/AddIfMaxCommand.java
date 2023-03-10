package сommands;

import result.Result;
import managers.Receiver;

public class AddIfMaxCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.addIfMax();
    }

}
