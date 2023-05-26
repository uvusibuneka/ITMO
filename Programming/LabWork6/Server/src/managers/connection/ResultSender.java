package managers.connection;

import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ResultSender {

    InetAddress host;
    int port;
    DatagramSocket ds;
    DatagramPacket dp;
    public ResultSender(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }

    public void send(Result<?> to_send){

        byte[] arr;
        arr = to_send // serialize
        dp = new DatagramPacket(arr, arr.length,host,port);
        try {
            ds.send(dp);
        } catch (IOException e) {
            System.out.println("Ошибка отправки ответа");
            dp = new DatagramPacket("Ошибка отправки ответа".getBytes(), arr.length,host,port);
            try {
                ds.send(dp);
            } catch (IOException ex) {
                System.out.println("Ошибка отправки сообщения об ошибке((((");
            }
        }

    }

    public int getPort() {
        return port;
    }

    public InetAddress getHost() {
        return host;
    }
}
