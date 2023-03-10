package Status;

import java.util.Objects;

public abstract class Status {
    protected Enum condition;

    public Enum getType() {
        return condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return condition == status.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition);
    }

    @Override
    public String toString() {
        return "Status.Status{" +
                "condition=" + condition +
                '}';
    }
}
