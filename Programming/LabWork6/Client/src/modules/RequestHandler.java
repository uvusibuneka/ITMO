package modules;

import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class RequestHandler {
    private final DatagramChannel channel;
    private final ByteBuffer buffer;
    private final int timeout;

    public RequestHandler(DatagramChannel channel, int bufferSize, int timeout) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(bufferSize);
        this.timeout = timeout;
    }

    public DatagramPacket receivePacket() throws IOException {
        buffer.clear();
        channel.receive(buffer);
        buffer.flip();
        return new DatagramPacket(buffer.array(), buffer.limit());
    }

    public Result<DatagramPacket> receivePacketWithTimeout() {
        try {
            channel.configureBlocking(false);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            selector.select(timeout);
            Set<SelectionKey> keys = selector.selectedKeys();
            if (keys.isEmpty()) {
                return Result.failure(new SocketTimeoutException());
            }
            Iterator<SelectionKey> iterator = keys.iterator();
            SelectionKey key = iterator.next();
            DatagramChannel datagramChannel = (DatagramChannel) key.channel();
            buffer.clear();
            datagramChannel.receive(buffer);
            buffer.flip();
            return Result.success(new DatagramPacket(buffer.array(), buffer.limit()));
        } catch (IOException e) {
            return Result.failure(e);
        }
    }
}