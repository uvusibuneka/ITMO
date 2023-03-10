package builders;

import music.Coordinates;

public class CoordinatesBuilder {
    private Long x;
    private Float y;

    public CoordinatesBuilder setX(Long x) {
        this.x = x;
        return this;
    }

    public CoordinatesBuilder setY(Float y) {
        this.y = y;
        return this;
    }

    public Coordinates createCoordinates() {
        return new Coordinates(x, y);
    }
}
