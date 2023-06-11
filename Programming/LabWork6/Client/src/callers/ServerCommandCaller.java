package callers;

import caller.Caller;
import common.descriptions.CommandDescription;
import modules.ObjectSender;

import java.io.IOException;

public class ServerCommandCaller extends Caller {

    private CommandDescription commandDescription;
    private ObjectSender objectSender;

    public ServerCommandCaller(CommandDescription commandDescription, ObjectSender objectSender) {
        this.commandDescription = commandDescription;
        this.objectSender = objectSender;
    }

    @Override
    public void call() {
        try {
            objectSender.sendObject((CommandDescription)commandDescription);
        } catch (IOException e) {
            throw new RuntimeException("Error while sending command description", e);
        }
    }
}
