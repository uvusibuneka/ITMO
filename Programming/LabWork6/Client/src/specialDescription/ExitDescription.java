package specialDescription;

import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

public class ExitDescription extends CommandDescription {
    public ExitDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("exit");
        this.setCaller(new specialClientCaller(()->{
            System.exit(0);
            }, this, objectSender));
    }
}
