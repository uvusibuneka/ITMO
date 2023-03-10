import java.util.ArrayList;

import static java.lang.Math.max;


public class Room extends Place implements Storage, Openable {

    protected ArrayList<Entity> objects = new ArrayList<>();
    protected Subject floor = new Subject("Пол помещения " + this.name, true, cord);
    protected int brightness = 0;
    protected Openable door = new Openable() {

        String name = "Дверь помещения " + Room.this.name;

        @Override
        public boolean isOpen() {
            return isOpen;
        }
        @Override
        public void open() {
            isOpen = true;
            System.out.println(this.name + " открыта");
        }

        @Override
        public void close() {
            isOpen = false;
            System.out.println(this.name + " закрыта");
        }

        @Override
        public void open(Person person) {
            isOpen = true;
            System.out.println(this.name + " открыта " + person.getName());
        }

        @Override
        public void close(Person person) {
            isOpen = false;
            System.out.println(this.name + " закрыта " + person.getName());
        }
    };
    protected boolean isOpen = false;
    public Room(String name, Location cord) {
        super(name, cord);
    }

    public Subject getFloor() {
        return floor;
    }


    @Override
    public void add(Entity entity) {
        objects.add(entity);
        System.out.println(entity.getName() + " находится в " + this.name);
        if(entity instanceof Lamp){
            this.brightness = max(this.brightness, ((Lamp) entity).getBrightness());
        }
    }

    @Override
    public void remove(Entity entity) {
        objects.remove(entity);
        System.out.println(entity.getName() + " не находится " + this.name);
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

    @Override
    public void open() {
        door.open();
        System.out.println(this.name + " открыта");
    }

    @Override
    public void close() {
        door.close();
        System.out.println(this.name + " закрыта");
    }

    @Override
    public void open(Person person) {
        door.open();
        System.out.println(this.name + " открыта " + person.getName());
    }

    @Override
    public void close(Person person) {
        door.close();
        System.out.println(this.name + " закрыта " + person.getName());
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    public void set(ArrayList<Entity> objects) {
        this.objects = objects;
    }

}

