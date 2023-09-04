package managers.file.decorators.DataBase;

import common.Collection;
import common.IDAccess;
import common.descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.AbstractParser;
import managers.BaseTextReceiver;
import managers.file.DBSavable;
import result.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DBLoader<T extends Comparable<T> & IDAccess & DBSavable> extends AbstractLoader {
    private final ResultSet resultSet;
    private int i;
    private final AbstractParser parser;

    public DBLoader(BaseTextReceiver textReceiver, ResultSet resultSet) {
        super(textReceiver);
        this.resultSet = resultSet;
        parser = new AbstractParser() {
            @Override
            public <T> T parseComposite(String s, Class<T> type) {
                return null;
            }
        };
    }

    public <D extends LoadDescription<T>> Collection<T> load(D description) throws SQLException, ArrayIndexOutOfBoundsException {
        Collection<T> res = new Collection<>();
        while (resultSet.next()){
            try {
                T element = enter(description).getValue();
                res.add((T) element);
            }catch (RuntimeException e){
                if (e.getCause() instanceof SQLException){
                    throw (SQLException) e.getCause();
                }
                if (e.getCause() instanceof ArrayIndexOutOfBoundsException){
                    throw (ArrayIndexOutOfBoundsException) e.getCause();
                }
            }
        }
        return res;
    }

    @Override
    public <D extends LoadDescription<?>> D enter(D description) {
        return super.enter(description);
    }

    @Override
    public <D extends LoadDescription<?>> D enterDate(D description) {
        try {
            description.setField(resultSet.getDate(description.getFieldName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return description;
    }

    @Override
    public <D extends LoadDescription<Enum>> D enterEnum(D description) {
        try {
            description.setField(description.getType().getEnumConstants()[resultSet.getInt(description.getFieldName())]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return description;
    }

    @Override
    public <D extends LoadDescription<?>> D enterWrapper(D description) {
        try {
            description.setField(resultSet.getObject(description.getFieldName(), description.getType()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return description;
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> description) {
        try {
            description.setField(resultSet.getString(description.getFieldName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return description;
    }

    @Override
    public  <D extends LoadDescription<?>> D enterComposite(D description) {
        description.getFields().forEach(this::enter);
        description.build();
        if (description.getFieldOfDescriptionSetter() != null)
            description.setField(description.getValue());
        return description;
    }
}
