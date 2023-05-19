package commands;

import result.Result;

/**
 * Class ExitCommand for ending the program.
 */
public class ExitCommand implements Command {

    /**
     * Method of executing the command.
     * @return result for executing the command (the result of the exit() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.saveCollection();
    }
}