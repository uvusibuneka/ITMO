package managers;

import builders.Buildable;

import java.util.List;
import java.util.function.Function;

public class LoadDescription<T> {
    protected String description;
    protected Class<?> type;
    protected Buildable<T> builder;
    protected Function<T, ?> fieldSetter;
    protected Function<Buildable<T>, T> build;
    protected List<LoadDescription<?>> fields;

    public LoadDescription(String description, Function<T, ?> fieldSetter, Buildable<T> builder, Class<?> type) {
        this.description = description;
        this.fieldSetter = fieldSetter;
        this.builder = builder;
        this.type = type;
    }

    public Function<T, ?> getFieldSetter() {
        return fieldSetter;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getType() {
        return type;
    }

    public List<LoadDescription<?>> getFields() {
        return fields;
    }

    public T build() {
        return build.apply(builder);
    }
}