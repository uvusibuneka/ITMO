package specialDescription;

import caller.Caller;
import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;

import java.io.IOException;

public class HelpDescription extends CommandDescription {

    public HelpDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("help", "Prints help information");
        this.setCaller(new specialClientCaller(interactiveMode::printHelp, this, objectSender){
            @Override
            public void call() {
                try {
                    objectSender.sendObject(interactiveMode.getCommandDescriptionMap().get("help"));
                    runnable.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            });
    }


}
