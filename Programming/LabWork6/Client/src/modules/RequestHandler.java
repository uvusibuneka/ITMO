package modules;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class RequestHandler {
    private final DatagramSocket socket;
    private final byte[] buffer;
    private final int timeout;

    public RequestHandler(DatagramSocket socket, int bufferSize, int timeout) {
        this.socket = socket;
        this.buffer = new byte[bufferSize];
        this.timeout = timeout;
    }

    public DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return packet;
    }

    public DatagramPacket receivePacketWithTimeout() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.setSoTimeout(timeout);
        try {
            socket.receive(packet);
        } catch (SocketTimeoutException e) {
            // Handle timeout for waiting server response

        } finally {
            socket.setSoTimeout(0);
        }
        return packet;
    }
}
