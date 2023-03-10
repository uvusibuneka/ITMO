import Status.*;

import java.util.Objects;


public class Clothes extends Subject {

    protected ClothesType type;

    public Clothes(String name, ClothesType type) {
        super(name);
        this.type = type;
    }

    public ClothesType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NotAlive.Clothes{" +
                "name='" + this.getName() + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(this.getName(), clothes.getName());
    }
}
