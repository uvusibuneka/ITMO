package сommands;

import result.Result;
import managers.Receiver;


public class AddCommand implements Command {

    private Receiver receiver;


    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.add();
    }

}
