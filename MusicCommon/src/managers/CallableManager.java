package managers;

import caller.Callable;


import java.util.HashMap;
import java.util.Map;

public class CallableManager {
    private Map<String, Callable> callers = new HashMap<String, Callable>();

    public void register(String name, Callable caller) {
        callers.put(name, caller);
    }

    public void call(String CallerName) {
        callers.get(CallerName).call();
    }

}