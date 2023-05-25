package managers;

import descriptions.LoadDescription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractLoader {
    protected BaseTextReceiver textReceiver;

    public AbstractLoader(BaseTextReceiver textReceiver) {
        this.textReceiver = textReceiver;
    }
    private static final Map<Class<? extends Number>, Function<String, ? extends Number>> PARSERS = new HashMap<>();

    static {
        PARSERS.put(Integer.class, Integer::valueOf);
        PARSERS.put(Long.class, Long::valueOf);
        PARSERS.put(Float.class, Float::valueOf);
        PARSERS.put(Double.class, Double::valueOf);
        PARSERS.put(Byte.class, Byte::valueOf);
        PARSERS.put(Short.class, Short::valueOf);
    }
     public <T extends LoadDescription<?>> T enter(T description) {
        if (isWrapper(description.getType())) {
            return (T) enterWrapper((LoadDescription<Number>) description);
        } else if (description.getType().equals(String.class)) {
            return (T) enterString((LoadDescription<String>) description);
        } else if (description.getType().isEnum()){
            return (T) enterEnum((LoadDescription<Enum>) description);
        } else {
            return enterComposite(description);
        }
    }

    public <T> T parse(String s, Class<?> type){
        if (isWrapper(type)) {
            return (T) parseNumber(s, (Class<? extends Number>) type);
        } else if (type.equals(String.class)) {
            return (T) s;
        } else if (type.isEnum()){
            return (T) parseEnum(s, (Class<Enum>) type);
        } else {
            return (T) parseComposite(s, type);
        }
    }

    public <T extends LoadDescription<?>> T enterWithMessage(String message, T description) {
        textReceiver.print(message);
        return enter(description);
    }

    public abstract <T extends LoadDescription<Enum>> T enterEnum(T description);

    public abstract <T extends LoadDescription<Number>> T enterWrapper(T description);
    public abstract LoadDescription<String> enterString(LoadDescription<String> description);

    public Number parseNumber(String s, Class<? extends Number> type){
        return PARSERS.get(type).apply(s);
    }

    public <T extends Enum<T>> Enum<T> parseEnum(String s, Class<T> type){
        return Enum.valueOf(type, s);
    }

    public abstract <T> T parseComposite(String s, Class<T> type);
    protected  <T extends LoadDescription<?>> T enterComposite(T description) {
        List<LoadDescription<?>> fields = description.getFields();
        for (var field : description.getFields()) {
            fields.add(enterWithMessage(field.getDescription(), field));
        }
        description.getFields().clear();
        description.setFieldsOfObject(fields);
        description.build();
        return description;
    }

    private boolean isWrapper(Class<?> type) {
        return type.equals(Integer.class) || type.equals(Long.class) || type.equals(Double.class)
                || type.equals(Float.class) || type.equals(Short.class) || type.equals(Byte.class)
                || type.equals(Character.class) || type.equals(Boolean.class);
    }

    public BaseTextReceiver getTextReceiver() {
        return textReceiver;
    }

    public void setTextReceiver(BaseTextReceiver textReceiver) {
        this.textReceiver = textReceiver;
    }
}
