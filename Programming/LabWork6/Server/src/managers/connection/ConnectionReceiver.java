package managers.connection;

import commands.HelpCommand;
import commands.LoginCommand;
import commands.RegisterCommand;
import common.descriptions.CommandDescription;
import managers.Invoker;
import managers.user.User;
import result.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConnectionReceiver {

    public ConnectionReceiver() {
    }

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
        InputController inputController = new InputController();

        while (true) {
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
