package commands;

import common.CommandDescription;
import managers.Invoker;
import receivers.MusicReceiver;
import receivers.Receiver;
import result.Result;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Class ExecuteScriptCommand for executing a script from a file.
 */
public class ExecuteScriptCommand extends Command<MusicReceiver> {
    List<CommandDescription> queue;
    Invoker invoker;
    public ExecuteScriptCommand(List<CommandDescription> queue, Invoker invoker){
        super(MusicReceiver.GetInstance());
        this.queue = queue;
        this.invoker = invoker;
    }

    /**
     * Method execute calls the executeScriptCommand() method of the receiver object.
     * @return result for executing the command (the result of the executeScriptCommand() method of the receiver object)
     */

    @Override
    public Result<?> execute() {
        List<Result<?>> results = new ArrayList<>();
        for (CommandDescription cd: queue){
            Result<?> tmp_res = invoker.executeCommand(cd.getName(), cd);
            if (tmp_res.isSuccess())
                results.add(tmp_res);
            else
                return Result.failure(tmp_res.getError().get(), tmp_res.getMessage());
        }
        return Result.success(results);
    }
}