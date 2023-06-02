package common.descriptions;

import common.builders.Buildable;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LoadDescription<T> implements Serializable {

    protected T value;
    protected String description;
    protected Class<T> type;
    protected Buildable<T> builder;
    protected Function<T, ?> fieldOfDescriptionSetter;
    protected Supplier<T> build;
    protected List<LoadDescription<?>> fields;

    public LoadDescription(String description, Function<T, ?> fieldSetter, Buildable<T> builder, Class<T> type) {
        this.description = description;
        this.fieldOfDescriptionSetter = fieldSetter;
        this.builder = builder;
        this.type = type;
        if (builder != null)
            this.build = builder::build;
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

    public Class<T> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
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
        value = build.get();
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