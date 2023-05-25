package callers;

import descriptions.CommandDescription;
import modules.ObjectSender;

public class specialClientCaller extends serverCommandCaller {
    private Runnable runnable;

    public specialClientCaller(Runnable runnable, CommandDescription commandDescription, ObjectSender objectSender) {
        super(commandDescription, objectSender);
        this.runnable = runnable;
    }

}
