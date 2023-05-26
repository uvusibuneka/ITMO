package callers;

import common.descriptions.CommandDescription;
import modules.ObjectSender;

public class specialClientCaller extends serverCommandCaller {
    private Runnable runnable;

    public specialClientCaller(Runnable runnable, CommandDescription commandDescription, ObjectSender objectSender) {
        super(commandDescription, objectSender);
        this.runnable = runnable;
    }

    @Override
    public void call() {
        super.call();
        runnable.run();
    }

}
