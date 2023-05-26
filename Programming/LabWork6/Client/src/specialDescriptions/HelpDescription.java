package specialDescriptions;

import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

public class HelpDescription extends CommandDescription {

    public HelpDescription(ObjectSender objectSender) {
        super("help");
        this.setCaller(new specialClientCaller(InteractiveMode.getInstance()::printHelp, this, objectSender));
    }
}
