package commands;

import receivers.Receiver;
import result.Result;

import java.util.Deque;

/**
 * Class HistoryCommand for displaying the last 7 commands (without their arguments).
 */
public class HistoryCommand extends Command {
    /**
     * history - collection of the last 7 commands (without their arguments).
     */
    private Deque<String> history;

    /**
     * Constructor for creating a command object.
     */

    public HistoryCommand(Deque<String> history) {
        super("history : display the last 7 commands (without their arguments)", 0);
        this.history = history;
    }

    /**
     * Method execute calls the printHistory() method of the receiver object.
     * @param receiver manager for executing the command
     * @return result of executing the command (the result of the printHistory() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.printHistory(history);
    }
}