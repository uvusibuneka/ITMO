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
        InetSocketAddress senderAddress = (InetSocketAddress) channel.receive(buffer);
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return new DatagramPacket(data, data.length, senderAddress.getAddress(), senderAddress.getPort());
    }

    public Result<DatagramPacket> receivePacketWithTimeout() {
        Selector selector;
        try {
            channel.configureBlocking(false);
        } catch (IOException e) {
            return Result.failure(e, "Failed to configure channel to non-blocking");
        }
        try {
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            selector.select(timeout);
        } catch (IOException e) {
            return Result.failure(e, "Failed to select channel");
        }

        long startTime = System.currentTimeMillis();

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = timeout - elapsedTime;

            if (remainingTime <= 0) {
                return Result.failure(new SocketTimeoutException("Timeout waiting for server response"), "Timeout waiting for server response");
            }

            try {
                if (selector.select(remainingTime) > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isReadable()) {
                            DatagramPacket packet = receivePacket();
                            keyIterator.remove();
                            return Result.success(packet);
                        }
                    }
                }
            }catch (Exception e){
                return Result.failure(e, "Failed to select channel");
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

