package modules;

import caller.Caller;
import callers.specialClientCaller;
import descriptions.CommandDescription;
import result.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CallableManager {
    private List<Caller> callers;

    private RequestHandler requestHandler;
    private static Map<String, specialClientCaller> specialCommands;

    static {
        specialCommands.put("exit", new exitCaller());
        specialCommands.put("execute_script", new executeScriptCaller());
        specialCommands.put("history", new specialClientCaller(InteractiveMode::history, new HistoryDescription(), objectSender));
    }

    public CallableManager(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void add(Caller caller) {
        callers.add(caller);
    }

    public Result<Void> callAll() {
        for (Caller caller: callers) {
            caller.call();
            try {
                requestHandler.receivePacketWithTimeout();
            } catch (IOException e) {
                return Result.failure(e, "Error with server connection");
            }
        }
        return Result.success(null);
    }

    public void clear() {
        callers.clear();
    }
}

