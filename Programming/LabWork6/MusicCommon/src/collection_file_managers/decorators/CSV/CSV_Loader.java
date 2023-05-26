package collection_file_managers.decorators.CSV;

import collection_file_managers.decorators.Parser;
import descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.BaseTextReceiver;

import java.util.List;

public class CSV_Loader extends AbstractLoader {
    private final String[] CSV_row;
    private int i;
    private final Parser parser;

    public CSV_Loader(BaseTextReceiver textReceiver, String CSV_row) {
        super(textReceiver);
        this.CSV_row = CSV_row.split(",");
        i = 0;
        parser = new Parser();
    }

    @Override
    public <T extends LoadDescription<Enum>> T enterEnum(T description) {
        description.setField(parser.parseEnum(CSV_row[i++], description.getType()));
        return description;
    }

    @Override
    public <T extends LoadDescription<?>> T enterWrapper(T description) {
        description.setField(parser.parseWrapper(CSV_row[i++], description.getType()));
        return description;
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> description) {
        description.setField(CSV_row[i++]);
        return description;
    }

    public  <T extends LoadDescription<?>> T enterComposite(T description) {
        description.getFields().forEach(field -> field = enterWithMessage(field.getDescription(), field));
        description.build();
        return description;
    }
}
