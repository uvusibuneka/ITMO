package specialDescription;

import callers.SpecialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

public class ExitDescription extends CommandDescription {
    public ExitDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("exit", "Выход");
        this.setCaller(new SpecialClientCaller(() -> {
                try {
                    objectSender.sendObject(interactiveMode.getCommandDescriptionMap().get("exit"));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                interactiveMode.exit();
                return null;
            }, this, objectSender));
    }
}
