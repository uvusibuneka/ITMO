/**

 The CoordinatesBuilder class is designed to create a Coordinates object with specified coordinates.
 */
package builders;
import common.Coordinates;
public class CoordinatesBuilder {
    private Long x; // The x coordinate
    private Float y; // The y coordinate

    /**
     * Sets the value of the x coordinate
     * @param x - the value of the x coordinate
     * @return CoordinatesBuilder
     */
    public CoordinatesBuilder setX(Long x) {
        if (x <= -129)
            throw new IllegalArgumentException("The value of the x coordinate must be greater than -129");
        this.x = x;
        return this;
    }

    /**
     * Sets the value of the y coordinate
     * @param y - the value of the y coordinate
     * @return CoordinatesBuilder
     */
    public CoordinatesBuilder setY(Float y) {
        if (y <= -420)
            throw new IllegalArgumentException("The value of the y coordinate must be greater than -420");
        this.y = y;
        return this;
    }

    /**
     * Creates a Coordinates object with the specified coordinates
     * @return Coordinates
     */
    public Coordinates createCoordinates() {
        return new Coordinates(x, y);
    }
}