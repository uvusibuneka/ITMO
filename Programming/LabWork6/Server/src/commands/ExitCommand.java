package commands;

import result.Result;

/**
 * Class ExitCommand for ending the program.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for creating a command object.
     */
    public ExitCommand() {
        super("exit : end the program (without saving to file)");
    }

    /**
     * Method of executing the command.
     * @return result for executing the command (the result of the exit() method of the receiver object)
     */
    @Override
    public Result<Void> execute() {
        return receiver.exit();
    }
}