import java.util.ArrayList;

public interface Storage {
    void add(Entity entity);
    void remove(Entity entity);
    boolean contains(Entity entity);
    boolean isEmpty();
    int size();
}
