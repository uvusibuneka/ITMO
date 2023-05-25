package specialDescriptions;

import callers.specialClientCaller;
import descriptions.CommandDescription;

public class historyDescription extends CommandDescription {
    public historyDescription() {
        super("history");
        this.setCaller(new specialClientCaller(() -> {
            System.out.println("History:");
            for (String command: history) {
                System.out.println(command);
            }
        }, this, objectSender));
    }




}
