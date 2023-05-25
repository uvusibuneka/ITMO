package managers;

import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnectionReceiver {
    public void run(Invoker invoker) {
        byte[] arr = new byte[10];
        int len = arr.length;
        DatagramSocket ds;
        DatagramPacket dp;
        InetAddress host;
        int port = 6789; // сделай переменной окрудения

        ResultSender rs = null;

        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        Result<?> res = null;

        while (true) {
            dp = new DatagramPacket(arr, len);
            try {
                ds.receive(dp);
                if (rs == null) {
                    rs = new ResultSender(dp.getAddress(), dp.getPort());
                }else{
                    if (rs.getPort() == dp.getPort() && rs.getHost() == dp.getAddress()){


                        // main program
                        res = invoker.executeCommand();

                        if ()
                            rs = null;

                    }else{
                        res = Result.failure(new Exception(""), "Я занят");
                    }
                }
                rs.send(res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
