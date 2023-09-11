package main;

import managers.Invoker;
import managers.connection.ConnectionReceiver;
import managers.connection.DatagramManager;
import org.apache.logging.log4j.Logger;
import receivers.MusicReceiver;
import receivers.UserReceiver;

import org.apache.logging.log4j.LogManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            try {
                UserReceiver.GetInstance();
                MusicReceiver.GetInstance();
                logger.info("Files loaded");
                byte[] arr = new byte[10*1024];
                int len = arr.length;
                int port;
                try {
                    port = Integer.parseInt(System.getenv("SERVER_PORT"));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Укажите в переменной SERVER_PORT порт, на котором будет работать приложение. Порт должен быть целым числом");
                } catch (SecurityException e) {
                    throw new SocketException("Не удалось получить доступ к указанному порту");
                }
                ConnectionReceiver connectionReceiver = new ConnectionReceiver();
                connectionReceiver.run(new Invoker(), new DatagramManager(port, arr, len));
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        connectionReceiver.getExecutorService().shutdown();
                        Main.logger.info("ConnectionReceiver shutdown finished");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Main.logger.error(e.getMessage(), "Server shutdown finished incorrectly");
                    }
                }));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL JDBC Driver is not found. ", e);
        }
    }
}
