package managers.connection;

import main.Main;
import managers.Invoker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionReceiver {
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void run(Invoker invoker, DatagramManager datagramManager) {
        while (true) {
            Main.logger.info("Listening started");
            executorService.execute(()->
            {
                InputController inputController = new InputController(datagramManager);
                inputController.getExecutorService().execute(() -> {
                    inputController.parse(datagramManager.receive(), invoker);});
            });
        }
    }


}
