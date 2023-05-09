package commands;

import managers.Invoker;
import receivers.Receiver;
import result.Result;

/**
 * Class ExecuteScriptCommand for executing a script from a file.
 */
public class ExecuteScriptCommand extends Command {

    /**
     * Constructor for creating a command object.
     * @param commandManager manager for command execution
     */
    private final Invoker commandManager;
    public ExecuteScriptCommand(Invoker commandManager){
        super("execute_script file_name : execute script from file. \"file_name is\" is a absolute path of file.", -1);
        this.commandManager = commandManager;
    }

    /**
     * Method execute calls the executeScriptCommand() method of the receiver object.
     * @param receiver receiver for command execution
     * @return result for executing the command (the result of the executeScriptCommand() method of the receiver object)
     */

    @Override
    public Result<Void> execute(Receiver receiver, String args[]) {
        return receiver.executeScript(commandManager, args);
    }
}