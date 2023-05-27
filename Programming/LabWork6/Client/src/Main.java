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
        int port = Integer.parseInt(System.getenv("PORT"));
        TextReceiver textReceiver = new TextReceiver();
        DatagramChannel channel = null;
        RequestHandler requestHandler = new RequestHandler(channel, 1024, 1000);
        ConsoleLoader loader = new ConsoleLoader(textReceiver);

        try {
            ObjectSender objectSender = new ObjectSender(InetAddress.getLocalHost(), port);
            channel = DatagramChannel.open();
            InteractiveMode.getInstance(textReceiver, loader, requestHandler, objectSender, new CallableManager(requestHandler)).start();
        } catch (UnknownHostException e) {
            textReceiver.println("Error while creating object sender");
        }catch (IOException e){
            textReceiver.println("Error while opening channel");
        }
    }
}