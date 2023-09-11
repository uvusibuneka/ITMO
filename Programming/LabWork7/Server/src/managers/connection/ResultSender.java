package managers.connection;

import main.Main;
import managers.Invoker;
import managers.user.User;
import result.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResultSender {

    User user;

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ResultSender(User user) {
        this.user = user;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.getExecutorService().shutdown();
                Main.logger.info("ResultSender shutdown finished");
            } catch (Exception e) {
                e.printStackTrace();
                Main.logger.error(e.getMessage(), "Server shutdown finished incorrectly");
            }
        }));
    }

    public void send(Result<?> to_send){
        if (to_send != null){
            DatagramPacket dp;
            DatagramSocket ds = user.getDm().getDS();
            try {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(byteStream);
                oos.writeObject(to_send);
                oos.flush();
                byte[] arr = byteStream.toByteArray();
                oos.close();
                byteStream.close();

                if (arr.length > ds.getSendBufferSize()){
                    ds.setSendBufferSize(arr.length);
                    Main.logger.warn("Сообщение очень большое, временно увеличен размер отправляемых сообщений с" + arr.length + " до " + ds.getSendBufferSize());
                }

                dp = new DatagramPacket(arr, arr.length, user.getHost(), user.getPort());
                ds.send(dp);
                user.refreshLastActivity();
                Main.logger.info("Result sent to user. Message: " + to_send.getMessage());
                ds.setSendBufferSize(9216);
            } catch (IOException e) {
                Main.logger.error(e.getMessage(), e);
                dp = new DatagramPacket("Ошибка отправки ответа".getBytes(), "Ошибка отправки ответа".getBytes().length, user.getHost(), user.getPort());
                try {
                    ds.send(dp);
                    user.refreshLastActivity();
                } catch (IOException ex) {
                    Main.logger.error("Error with sending message about error");
                }
            }
        }
    }
}
