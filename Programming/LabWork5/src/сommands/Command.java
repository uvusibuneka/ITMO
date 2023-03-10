package сommands;

import Result.Result;
import managers.Receiver;

public interface Command {
    Result<Void> execute(Receiver receiver);
}