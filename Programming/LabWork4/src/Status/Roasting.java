package Status;

import java.util.Objects;

public class Roasting extends Status {
    protected Frying condition;

    public Frying getRoasting() {
        return condition;
    }

    public void setRoasting(Frying condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Roasting roasting1 = (Roasting) o;
        return condition == roasting1.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), condition);
    }

    @Override
    public String toString() {
        return "Status.Roasting{" +
                "roasting=" + condition +
                '}';
    }
}