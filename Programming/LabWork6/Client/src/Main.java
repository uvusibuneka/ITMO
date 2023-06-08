import loaders.ConsoleLoader;
import modules.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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

        RequestHandler requestHandler = null;
        try {
            requestHandler = new RequestHandler(channel, 4096, 6000);
        } catch (IOException e) {
            textReceiver.println("Error while creating request handler");
            System.exit(0);
        }
        ConsoleLoader loader = new ConsoleLoader(textReceiver);

        try {
            ObjectSender objectSender = new ObjectSender(InetAddress.getLocalHost(), port, channel);
            InteractiveMode.getInstance(textReceiver, loader, requestHandler, objectSender, new CallableManager(requestHandler)).start();
        } catch (Exception e) {
            textReceiver.println("Error while creating object sender");
        }
    }
}