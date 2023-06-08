package modules;

import result.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestHandler {
    private final DatagramChannel channel;
    private final ByteBuffer buffer;
    private final int timeout;

    public RequestHandler(DatagramChannel channel, int bufferSize, int timeout) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(bufferSize);
        this.timeout = timeout;
    }

    public Result<DatagramPacket> receivePacketWithTimeout() {
        try {
            channel.configureBlocking(false);
            channel.socket().setSoTimeout(timeout);
            buffer.clear();

            if (channel.receive(buffer) == null) {
                throw new SocketTimeoutException("Timeout reached");
            }

            buffer.flip();
            return Result.success(new DatagramPacket(buffer.array(), buffer.limit()));
        } catch (IOException e) {
            return Result.failure(e);
        }
    }

}
