package specialDescriptions;

import callers.specialClientCaller;
import descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;


public class HistoryDescription extends CommandDescription {
    public HistoryDescription(ObjectSender objectSender) {
        super("history");
        this.setCaller(new specialClientCaller(InteractiveMode.getInstance()::history, this, objectSender));
    }

}
