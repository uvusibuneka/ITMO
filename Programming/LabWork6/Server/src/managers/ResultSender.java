package managers;

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

        dp = new DatagramPacket(arr, arr.length,host,port);
        try {
            ds.send(dp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
