package callers;

import caller.Caller;
import descriptions.CommandDescription;
import modules.ObjectSender;

import java.io.IOException;

public class serverCommandCaller extends Caller {

    private CommandDescription commandDescription;
    private ObjectSender objectSender;

    public serverCommandCaller(CommandDescription commandDescription, ObjectSender objectSender) {
        this.commandDescription = commandDescription;
        this.objectSender = objectSender;
    }

    @Override
    public void call() {
        try {
            objectSender.sendObject(commandDescription);
        } catch (IOException e) {
            throw new RuntimeException("Error while sending command description", e);
        }
    }
}
