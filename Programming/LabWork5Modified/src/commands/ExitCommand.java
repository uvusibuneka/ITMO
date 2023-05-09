package commands;

import receivers.Receiver;
import result.Result;

/**
 * Class ExitCommand for ending the program.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for creating a command object.
     */

    public ExitCommand() {
        super("exit : end the program (without saving to file)", 0);
    }

    /**
     * Method of executing the command.
     * @param receiver receiver for command execution
     * @return result for executing the command (the result of the exit() method of the receiver object)
     */
    @Override
    public Result<Void> execute(Receiver receiver, String[] args) {
        return receiver.exit();
    }
}