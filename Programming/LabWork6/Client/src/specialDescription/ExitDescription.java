package specialDescription;

import caller.Caller;
import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

public class ExitDescription extends CommandDescription {
    public ExitDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("exit", "Выход");
        this.setCaller(new specialClientCaller(() -> {
                try {
                    objectSender.sendObject(interactiveMode.getCommandDescriptionMap().get("exit"));
                    interactiveMode.exit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, this, objectSender));
    }
}
