package managers.connection;

import managers.user.User;
import result.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ResultSender {

    User user;
    DatagramSocket ds;
    DatagramPacket dp;
    public ResultSender(User user, DatagramSocket ds) throws SocketException {
        this.user = user;
        this.ds = ds;
    }

    public void send(Result<?> to_send){
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteStream);
            oos.writeObject(to_send);
            oos.flush();
            byte[] arr = byteStream.toByteArray();
            oos.close();
            byteStream.close();
            dp = new DatagramPacket(arr, arr.length, user.getHost(), user.getPort());
            ds.send(dp);
            user.refreshLastActivity();
        } catch (IOException e) {
            System.out.println("Ошибка отправки ответа");
            dp = new DatagramPacket("Ошибка отправки ответа".getBytes(), "Ошибка отправки ответа".getBytes().length, user.getHost(), user.getPort());
            try {
                ds.send(dp);
                user.refreshLastActivity();
            } catch (IOException ex) {
                System.out.println("Ошибка отправки сообщения об ошибке((((");
            }
        }
    }
}
