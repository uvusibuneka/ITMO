import java.util.ArrayList;

public class Place extends Entity implements Storage {

    protected ArrayList<Entity> objects = new ArrayList<Entity>();

    public Location getLocation() {
        return this.cord;
    }

    public Place(String name, Location cord) {
        super(name, cord);
    }

    @Override
    public void add(Entity entity) {
        System.out.println("В " + this.name + "  находится " + entity.getName());
    }

    @Override
    public void remove(Entity entity) {
        System.out.println("Из " + this.name + " переместился " + entity.getName());
    }

    @Override
    public boolean contains(Entity entity) {
        return objects.contains(entity);
    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    @Override
    public int size() {
        return objects.size();
    }

}