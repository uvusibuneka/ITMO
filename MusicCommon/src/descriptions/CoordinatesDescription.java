package descriptions;

import builders.Buildable;
import builders.CoordinatesBuilder;
import common.Coordinates;
import managers.LoadDescription;

import java.util.Arrays;
import java.util.function.Function;

public class CoordinatesDescription extends LoadDescription<Coordinates> {
    {
        build = Buildable::build;
        fields = Arrays.asList(
                new LoadDescription<Long>("X", (new CoordinatesBuilder())::setX, null, Integer.class),
                new LoadDescription<Float>("Y", (new CoordinatesBuilder())::setY, null, Float.class));
    }

    public CoordinatesDescription(Function<Coordinates, Object> fieldSetter) {
        super("Coordinates of Music Band", fieldSetter, new CoordinatesBuilder(), Coordinates.class);
    }

    public CoordinatesDescription() {
        super("Coordinates of Music Band", null, new CoordinatesBuilder(), Coordinates.class);
    }

}
