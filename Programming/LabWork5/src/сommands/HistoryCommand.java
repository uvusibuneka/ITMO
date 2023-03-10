package сommands;
import Result.Result;
import managers.Receiver;

import java.util.Deque;

public class HistoryCommand implements Command {
    private Deque<String> history;

    public HistoryCommand(Deque<String> history) {
        this.history = history;
    }

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.printHistory(history);
    }
}