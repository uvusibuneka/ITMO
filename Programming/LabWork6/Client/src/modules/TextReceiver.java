package modules;

import managers.BaseTextReceiver;

import java.util.Collection;

public class TextReceiver implements BaseTextReceiver {
    public void print(String message) {
        System.out.print(message);
    }
    public void println(String message) {
        System.out.println(message);
    }

    public void println(Collection c) {
        c.stream().forEach(System.out::println);
    }
}
