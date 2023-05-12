package modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import managers.AbstractLoader;
import managers.BaseTextReceiver;
import modules.TextReceiver;

public class Loader extends AbstractLoader {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public <T> T enterWrapper(String message, Class<T> type, BaseTextReceiver textReceiver){
        textReceiver.print(message);
        try {
            return (T) reader.readLine().valueOf(type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String enterString(String message, BaseTextReceiver textReceiver){
        textReceiver.print(message);
        try {
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
