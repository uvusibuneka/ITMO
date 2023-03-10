import java.util.Objects;

public class Entity {
    protected String name;
    protected Location cord;

    public void setLocation(int x, int y, int z) {
        this.cord = new Location(x, y, z);
    }

    public Entity(String name, Location cord) {
        this.name = name;
        this.cord = cord;
    }

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name) &&
                Objects.equals(cord, entity.cord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cord);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", cord=" + cord +
                '}';
    }
}