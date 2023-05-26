package managers.connection;

import managers.Invoker;
import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnectionReceiver {

    ResultSender rs;
    public void run(Invoker invoker) throws SocketException {
        byte[] arr = new byte[1024];
        int len = arr.length;
        DatagramSocket ds;
        DatagramPacket dp;
        InetAddress host;
        int port = Integer.parseInt(System.getenv("SERVER_PORT"));

        rs = null;

        ds = new DatagramSocket(port);

        Result<?> res = null;

        while (true) {
            dp = new DatagramPacket(arr, len);
            try {
                ds.receive(dp);
                if (rs == null) {
                    rs = new ResultSender(dp.getAddress(), dp.getPort());
                }else{
                    if (rs.getPort() == dp.getPort() && rs.getHost() == dp.getAddress()){

                        //десериализовать
                        res = invoker.executeCommand();

                        if ()//name == exit
                            rs = null;

                    }else{
                        res = Result.failure(new Exception(""), "Занят занят работой с другим клиентом");
                    }
                }
                rs.send(res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close_client(){
        rs = null;
    }
}
