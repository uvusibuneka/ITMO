package modules;

import caller.Caller;
import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import result.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CallableManager {
    private List<Caller> callers;
    private RequestHandler requestHandler;

    public CallableManager(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void add(Caller caller) {
        callers.add(caller);
    }

    public List<Result<?>> callAll() {
        return callers.stream().map(caller -> {
            caller.call();
            try {
                return requestHandler.receiveResult();
            } catch (Exception e) {
                return Result.failure(e, "Error with server connection");
            }
        });
    }

    public void clear() {
        callers.clear();
    }
}

