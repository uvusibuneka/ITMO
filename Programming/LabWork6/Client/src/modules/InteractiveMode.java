package modules;

import managers.CallableManager;

public class InteractiveMode {
    private final CallableManager callableManager;
    private TextReceiver textReceiver;
    private Loader loader;

    private RequestHandler requestHandler;

    public InteractiveMode(TextReceiver textReceiver, Loader loader, RequestHandler requestHandler, ObjectSender objectSender, CallableManager callableManager) {
        this.textReceiver = textReceiver;
        this.loader = loader;
        this.requestHandler = requestHandler;
        this.callableManager = callableManager;

    }

    public void start() {
        textReceiver.print("Interactive mode started! Check command help to see available commands.");
        while (true){
            String command = loader.enterString("Enter command: ", textReceiver);
            // эту херню обязательно надо поменять, хз как, но надо
            if (command.equals("exit")) {
                textReceiver.print("Interactive mode finished!");
                break;
            }
            //-----------------------------

            textReceiver.print("Unknown command!");
        }
    }


}
