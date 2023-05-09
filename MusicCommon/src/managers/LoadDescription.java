package managers;

import builders.Buildable;

import java.util.List;
import java.util.function.Function;

public class LoadDescription<T> {
    private String description;
    private Class<?> type;

    Buildable<T> builder;
    private Function<T, T> fieldBuilder;

    private Function<Buildable<T>, T> build;

    private List<LoadDescription<?>> fields;

    public LoadDescription(String description, Class<?> type, Function<T, T> fieldBuilder) {
        this.description = description;
        this.type = type;
        this.fieldBuilder = fieldBuilder;
    }

    public void setField(Object object){
        fieldBuilder.apply(object);
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
