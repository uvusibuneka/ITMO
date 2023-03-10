package сommands;

import result.Result;
import managers.Invoker;
import managers.Receiver;

public class ExecuteScriptCommand implements Command {
    private final Invoker commandManager;

    public ExecuteScriptCommand(Invoker commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.executeScriptCommand(commandManager);
    }
}