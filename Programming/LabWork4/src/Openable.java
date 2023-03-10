public interface Openable {

    void open();
    void close();
    void open(Person person);
    void close(Person person);

    boolean isOpen();
}
