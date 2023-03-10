package music;

public class Coordinates {
    static final float epsilon = 0.01f;
    private Long x;
    private Float y;

    public Coordinates(Long x, Float y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Fields x and y cannot be null");
        }
        if (x <= -129) {
            throw new IllegalArgumentException("Field x must be greater than -129");
        }
        if (y <= -420) {
            throw new IllegalArgumentException("Field y must be greater than -420");
        }
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(Long x) {
        if (x == null) {
            throw new IllegalArgumentException("Field x cannot be null");
        }
        if (x <= -129) {
            throw new IllegalArgumentException("Field x must be greater than -129");
        }
        this.x = x;
    }

    public void setY(Float y) {
        if (y == null) {
            throw new IllegalArgumentException("Field y cannot be null");
        }
        if (y <= -420) {
            throw new IllegalArgumentException("Field y must be greater than -420");
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "MusicClasses.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int compareTo(Coordinates coordinates) {
        int result = Long.compare(this.x, coordinates.x);
        if (result != 0) {
            return result;
        }

        result = Float.compare(this.y, coordinates.y);
        if (Math.abs(this.y - coordinates.y) < epsilon) {
            return 0;
        } else if (this.y < coordinates.y) {
            return -1;
        } else {
            return 1;
        }

    }
}

