package commands;

import receivers.Receiver;
import result.Result;

import java.io.BufferedReader;
import java.util.Map;

/**
 *
 * Class HelpCommand for displaying help for available commands.
 */

public class HelpCommand extends Command {

    /**
     * List of commands.
     * Key - command name. Value - command object.
     */
    private final Map<String, Command> commands;

    /**
     * Reader for reading commands from the console.
     */

    private BufferedReader primaryReader;

    /**
     * Constructor for creating a command object.
     */
    public HelpCommand(Map<String, Command> commands){
        super("help : display help for available commands", 0);
        this.commands = commands;
    }

    /**
     * Method execute calls the printHelpInfo() method of the receiver object.
     * @param receiver an object that will execute the command
     * @return the result of executing the command (the result of the printHelpInfo() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.printHelpInfo(commands);
    }
}
