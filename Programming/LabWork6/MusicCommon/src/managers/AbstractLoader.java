package managers;

import descriptions.LoadDescription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractLoader {
    protected BaseTextReceiver textReceiver;

    protected AbstractParser parser;

    public AbstractLoader(BaseTextReceiver textReceiver) {
        this.textReceiver = textReceiver;
    };

    public <T> T parse(String s, Class<?> type){
        return parser.parse(s, type);
    }

    public <T extends LoadDescription<?>> T enter(T description) {
        if (isWrapper(description.getType())) {
            return (T) enterWrapper(description);
        } else if (description.getType().equals(String.class)) {
            return (T) enterString((LoadDescription<String>) description);
        } else if (description.getType().isEnum()){
            return (T) enterEnum((LoadDescription<Enum>) description);
        } else {
            return enterComposite(description);
        }
    }


    public <T extends LoadDescription<?>> T enterWithMessage(String message, T description) {
        textReceiver.print(message);
        return enter(description);
    }

    public abstract <T extends LoadDescription<Enum>> T enterEnum(T description);

    public abstract <T extends LoadDescription<?>> T enterWrapper(T description);
    public abstract LoadDescription<String> enterString(LoadDescription<String> description);

    protected  <T extends LoadDescription<?>> T enterComposite(T description) {
        List<LoadDescription<?>> fields = description.getFields();
        description.getFields().forEach(field -> field = enterWithMessage(field.getDescription(), field));
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
