package modules;

import managers.BaseTextReceiver;

public class TextReceiver implements BaseTextReceiver {
    public void print(String message) {
        System.out.print(message);
    }
    public void println(String message) {
        System.out.println(message);
    }
}
