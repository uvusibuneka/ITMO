package common.descriptions;

import common.builders.Buildable;
import common.builders.CoordinatesBuilder;
import common.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class CoordinatesDescription extends LoadDescription<Coordinates> implements Serializable {
    protected CoordinatesBuilder builder = new CoordinatesBuilder();
    {
        fields = new ArrayList<>(Arrays.asList(
                new LoadDescription<Long>("X", builder::setX, null, Long.class),
                new LoadDescription<Float>("Y", builder::setY, null, Float.class)));
    }

    public CoordinatesDescription(SerialFunction<Coordinates, Object> fieldSetter) {
        super("Coordinates of Music Band", fieldSetter, Coordinates.class);
    }


}
