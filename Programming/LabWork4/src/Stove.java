import Status.ColorType;

import java.util.ArrayList;

public class Stove extends Place implements Openable {

    protected boolean isOpen = false;
    protected ArrayList<Entity> objects = new ArrayList<Entity>();
    protected ColorType color;

    public Stove(String name, Location cord) {
        super(name, cord);
    }

    @Override
    public void close() {
        System.out.println("Печка закрылась");
    }

    public void toCook() {
        for(Entity entity : objects) {
            if (entity instanceof Food) {
                System.out.println("В печке готовится " + entity.getName());
            }else{
                System.out.println(entity.getName() + " горит в печке");
            }
        }
    }

    public void set(ArrayList<Entity> objects) {
        this.objects = objects;
    }

    @Override
    public void open(Person person) {
        System.out.println("Печка открыта " + person.getName());
    }

    @Override
    public void close(Person person) {
        System.out.println("Печка закрыта " + person.getName());
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void add(Entity entity) {
        objects.add(entity);
        System.out.println(entity.getName() + " положили в печку");
    }

    @Override
    public void open() {
        System.out.println("Печка открылась ");
    }


    @Override
    public void remove(Entity entity) {
        objects.remove(entity);
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

