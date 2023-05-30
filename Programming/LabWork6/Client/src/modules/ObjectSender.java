package modules;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ObjectSender {
    SocketAddress addr;
    public ObjectSender(InetAddress host, int port) {
        addr = new InetSocketAddress(host,port);
    }

    public void sendObject(Object object) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(object);
        objectStream.flush();
        byte[] bytes = byteStream.toByteArray();

        DatagramChannel dc = DatagramChannel.open();

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        dc.send(buf, addr);
    }
}
