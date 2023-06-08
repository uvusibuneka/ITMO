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

    public RequestHandler(DatagramChannel channel, int bufferSize, int timeout) throws IOException {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(bufferSize);
        this.timeout = timeout;
        channel.configureBlocking(false);
    }

    public void closeChannel() throws IOException {
        channel.close();
    }

    public Result<DatagramPacket> receivePacketWithTimeout() {
        try {
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            if (selector.select(timeout) == 0) {
                throw new SocketTimeoutException("Timeout reached");
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isReadable()) {
                    buffer.clear();
                    channel.receive(buffer);
                    buffer.flip();
                    DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.limit());
                    return Result.success(packet);
                }
                keyIterator.remove();
            }

            throw new SocketTimeoutException("Timeout reached");
        } catch (IOException e) {
            return Result.failure(e);
        }
    }
}
