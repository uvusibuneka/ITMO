import managers.RequestHandler;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ConnectionModule {
    private DatagramSocket socket;
    private byte[] buffer;

    public ConnectionModule(int port) throws Exception {
        // Создаем сокет для прослушивания входящих подключений
        socket = new DatagramSocket(port);
        buffer = new byte[1024];
    }

    public void start() throws Exception {
        System.out.println("Server started on port " + socket.getLocalPort());

        while (true) {
            // Бесконечный цикл для ожидания новых подключений
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            // При получении нового подключения создание нового потока для обработки запросов клиента
            Thread thread = new Thread(new RequestHandler(socket, clientAddress, clientPort, request.getData()));
            thread.start();
        }
    }

    public void stop() {
        socket.close();
    }
}
