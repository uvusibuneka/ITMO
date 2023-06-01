package managers.connection;

import common.descriptions.CommandDescription;
import managers.Invoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import result.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnectionReceiver {

    private static final Logger logger = LogManager.getLogger(ConnectionReceiver.class);

    public void run(Invoker invoker) throws SocketException, NumberFormatException {
        byte[] arr = new byte[1024];
        int len = arr.length;
        DatagramSocket ds;
        DatagramPacket dp;
        InetAddress host;
        try {
            int port = Integer.parseInt(System.getenv("SERVER_PORT"));
            ds = new DatagramSocket(port);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Укажите в переменной SERVER_PORT порт, на котором будет работать приложение. Порт должен быть целым числом");
        } catch (SocketException | SecurityException e) {
            throw new SocketException("Не удалось получить доступ к указанному порту");
        }

        Result<?> res = null;
        InputController inputController = new InputController(ds);

        while (true) {
            logger.info("Listening started");
            dp = new DatagramPacket(arr, len);
            try {
                ds.receive(dp);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(dp.getData());
                ObjectInputStream ois = new ObjectInputStream(byteStream);
                CommandDescription cd = (CommandDescription) ois.readObject();

                inputController.parse(cd, invoker, dp);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
