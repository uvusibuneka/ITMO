import java.util.Objects;

public class Food extends Subject {

    public Food(String name) {
        super(name, false);
    }

    @Override
    public String toString() {
        return "Food{" +
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
        Food food = (Food) o;
        return Objects.equals(this.getName(), food.getName());
    }
}
