package descriptions;

import builders.Buildable;

import java.util.List;
import java.util.function.Function;

public class LoadDescription<T> {

    protected T value;
    protected String description;
    protected Class<?> type;
    protected Buildable<T> builder;
    protected Function<T, ?> fieldOfDescriptionSetter;
    protected Function<Buildable<T>, T> build;
    protected List<LoadDescription<?>> fields;

    public LoadDescription(String description, Function<T, ?> fieldSetter, Buildable<T> builder, Class<T> type) {
        this.description = description;
        this.fieldOfDescriptionSetter = fieldSetter;
        this.builder = builder;
        this.type = type;
    }

    public LoadDescription(Class<T> type){
        this(null, null, null, type);
    }

    public Function<T, ?> getFieldOfDescriptionSetter() {
        return fieldOfDescriptionSetter;
    }

    public Buildable<T> getBuilder() {
        return builder;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getType() {
        return type;
    }

    public void setField(Object object){
        if(object.getClass() == type)
            fieldOfDescriptionSetter.apply((T)object);
        else
            throw new IllegalArgumentException("Wrong type of field");
    }
    public List<LoadDescription<?>> getFields() {
        return fields;
    }

    public void build() {
        value = build.apply(builder);
    }

    public void setFieldsOfObject(List<LoadDescription<?>> fields) {
        this.fields = fields;
    }

    public LoadDescription<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setFieldOfDescriptionSetter(Function<T, ?> fieldOfDescriptionSetter) {
        this.fieldOfDescriptionSetter = fieldOfDescriptionSetter;
    }



}