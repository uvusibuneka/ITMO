package managers;

import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnectionReceiver {
    public void run(Invoker invoker){
        byte[] arr = new byte[10];
        int len = arr.length;
        DatagramSocket ds; DatagramPacket dp;
        InetAddress host; int port = 6789;

        ResultSender rs;

        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        dp = new DatagramPacket(arr,len);
        try {
            ds.receive(dp);
            rs = new ResultSender(dp.getAddress(), dp.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // main program
        Result<?> res = invoker.executeCommand();

        rs.send(res);
    }
}
