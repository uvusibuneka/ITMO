package managers;

public abstract class AbstractLoader {

    public <T> void enter(T object, String message, LoadDescription<?> description, BaseTextReceiver textReceiver) {
        if (isWrapper(description.getType())) {
            enterWrapper(object, message, textReceiver);
        } else if (description.getType().equals(String.class)) {
            enterString((String) object, message, textReceiver);
        } else if (description.getType().isEnum()){
            enterEnum(object, message, textReceiver);
        }
        else {
            enterComposite(object, message, (LoadDescription<T>) description, textReceiver);
        }
    }
    public abstract  <T> void enterEnum(T object, String message, BaseTextReceiver textReceiver);

    public abstract <T> void enterWrapper(T object, String message, BaseTextReceiver textReceiver);

    public abstract void enterString(String s, String message, BaseTextReceiver textReceiver);

    public <T> void enterComposite(T object, String message, LoadDescription<T> description, BaseTextReceiver textReceiver) {
        textReceiver.print(message);
        object = description.builder.build();
        for (var field : description.getFields()) {
            enter((T)object, field.getDescription(), field, textReceiver);
        }
    }

    private boolean isWrapper(Class<?> type) {
        return type.equals(Integer.class) || type.equals(Long.class) || type.equals(Double.class)
                || type.equals(Float.class) || type.equals(Short.class) || type.equals(Byte.class)
                || type.equals(Character.class) || type.equals(Boolean.class);
    }
}

