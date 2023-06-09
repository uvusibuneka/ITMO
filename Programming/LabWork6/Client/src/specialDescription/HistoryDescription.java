package specialDescription;

import caller.Caller;
import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

import java.io.IOException;


public class HistoryDescription extends CommandDescription {
    public HistoryDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("history", "Prints history of commands");
        this.setCaller(new specialClientCaller(interactiveMode::history, this, objectSender){
            @Override
            public void call() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

}
