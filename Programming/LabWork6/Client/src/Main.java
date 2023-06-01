import loaders.ConsoleLoader;
import modules.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;

public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Программа завершает работу.")));
        int port = 0;
        try {
            port = Integer.parseInt(System.getenv("PORT"));
        }catch (NumberFormatException e){
            System.out.println("Error while parsing port");
            System.exit(0);
        }
        TextReceiver textReceiver = new TextReceiver();
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
        }
        catch (IOException e){
            textReceiver.println("Error while opening channel");
            System.exit(0);
        }
        RequestHandler requestHandler = new RequestHandler(channel, 1024, 6000);
        ConsoleLoader loader = new ConsoleLoader(textReceiver);

        try {
            ObjectSender objectSender = new ObjectSender(InetAddress.getLocalHost(), port);
            InteractiveMode.getInstance(textReceiver, loader, requestHandler, objectSender, new CallableManager(requestHandler)).start();
        } catch (Exception e) {
            textReceiver.println("Error while creating object sender");
        }
    }
}