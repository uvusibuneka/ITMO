package Status;

import java.util.Objects;

public class Color extends Status {
        protected ColorType condition;

        public ColorType getColor() {
            return condition;
        }
        public void setColor(ColorType condition) {
            this.condition = condition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Color that = (Color) o;
            return condition == that.condition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), condition);
        }

        @Override
        public String toString() {
            return "Status.Color{" +
                    "color=" + condition +
                    '}';
        }
    }

