import managers.Invoker;
import managers.connection.ConnectionReceiver;


import java.net.SocketException;
public class Main {

    public static void main(String[] args) {
        try {
            (new ConnectionReceiver()).run(new Invoker());
        } catch (SocketException e) {
            System.out.println("Не удалось получить доступ к указанному порту");
        } catch (NumberFormatException e) {
            System.out.println("Укажите в переменной SERVER_PORT порт, на котором будет работать приложение. Порт должен быть целым числом");
        }
    }
}
