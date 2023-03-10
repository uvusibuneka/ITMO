package сommands;

import result.Result;
import managers.Receiver;

public interface Command {
    Result<Void> execute(Receiver receiver);
}