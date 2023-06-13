package specialDescription;

import callers.SpecialClientCaller;
import common.descriptions.CommandDescription;
import modules.InteractiveMode;
import modules.ObjectSender;
import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;

public class HelpDescription extends CommandDescription {

    public HelpDescription(ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("help", "Prints help information");
        this.setCaller(new SpecialClientCaller(() -> {
                interactiveMode.printHelp();
                try {
                    interactiveMode.getRequestHandler().receivePacketWithTimeout();
                }catch (Exception e){
                    throw new RuntimeException("Error with connection");
                }
                return null;
            } , this, objectSender){
            @Override
            public void call() {
                try {
                    objectSender.sendObject(interactiveMode.getCommandDescriptionMap().get("help"));
                    supplier.get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            });
    }


}
