package specialDescription;

import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;


public class HistoryDescription extends CommandDescription {
    public HistoryDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("history");
        this.setCaller(new specialClientCaller(interactiveMode::history, this, objectSender));
    }

}
