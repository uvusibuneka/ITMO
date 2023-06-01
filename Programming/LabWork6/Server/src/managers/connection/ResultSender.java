package managers.connection;

import managers.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import result.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ResultSender {
    private static final Logger logger = LogManager.getLogger(ResultSender.class);

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
            logger.info("Result sent to user");
        } catch (IOException e) {
            logger.error("Error with sending");
            dp = new DatagramPacket("Ошибка отправки ответа".getBytes(), "Ошибка отправки ответа".getBytes().length, user.getHost(), user.getPort());
            try {
                ds.send(dp);
                user.refreshLastActivity();
            } catch (IOException ex) {
                logger.error("Error with sending message about error");
            }
        }
    }
}
