package managers;

public abstract class AbstractLoader {

    public <T> T enter(String message, LoadDescription<T> description, TextReceiver textReceiver){
        if (isWrapper(description.getType())) {
            return (T) enterWrapper(message, description.getType(), textReceiver);
        } else if (description.getType().equals(String.class)) {
            return (T) enterString(message, textReceiver);
        } else {
            return enterComposite(message, description, textReceiver);
        }
    }

    public abstract <T> T enterWrapper(String message, Class<T> type, TextReceiver textReceiver);

    public abstract String enterString(String message, TextReceiver textReceiver);

    public <T> T enterComposite(String message, LoadDescription<T> description, TextReceiver textReceiver) {
        textReceiver.print(message);
        for (var field : description.getFields()) {
            Object fieldValue = enter(field.getDescription(), field, textReceiver);
            field.setField(fieldValue);
        }
        return description.build();
    }

    private boolean isWrapper(Class<?> type) {
        return type.equals(Integer.class) || type.equals(Long.class) || type.equals(Double.class)
                || type.equals(Float.class) || type.equals(Short.class) || type.equals(Byte.class)
                || type.equals(Character.class) || type.equals(Boolean.class);
    }
}
