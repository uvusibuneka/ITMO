package specialDescription;

import callers.SpecialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

public class HistoryDescription extends CommandDescription {

    public HistoryDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("history", "Prints history of commands");
        this.setCaller(new SpecialClientCaller(() -> {
            interactiveMode.history();
            return null;
        }, this, objectSender));
    }
}
