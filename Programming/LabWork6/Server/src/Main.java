import managers.Invoker;
import managers.connection.ConnectionReceiver;
import receivers.MusicReceiver;
import receivers.UserReceiver;


import java.net.SocketException;
public class Main {

    public static void main(String[] args) {
        try {
            UserReceiver.GetInstance();
            MusicReceiver.GetInstance();
            (new ConnectionReceiver()).run(new Invoker());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
