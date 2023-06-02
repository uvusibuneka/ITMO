package common.descriptions;

import common.builders.Buildable;
import common.builders.CoordinatesBuilder;
import common.Coordinates;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

public class CoordinatesDescription extends LoadDescription<Coordinates> implements Serializable {
    {
        fields = Arrays.asList(
                new LoadDescription<>("X", new CoordinatesBuilder()::setX, null, Long.class),
                new LoadDescription<>("Y", (new CoordinatesBuilder())::setY, null, Float.class));
    }

    public CoordinatesDescription(Function<Coordinates, Object> fieldSetter) {
        super("Coordinates of Music Band", fieldSetter, new CoordinatesBuilder(), Coordinates.class);
    }

    public CoordinatesDescription() {
        super("Coordinates of Music Band", null, new CoordinatesBuilder(), Coordinates.class);
    }

}
