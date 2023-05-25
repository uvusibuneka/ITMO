package collection_file_managers.decorators;

import common.IDAccess;
import collection_file_managers.Abstract_file_reader;
import descriptions.LoadDescription;

import java.io.FileNotFoundException;

public abstract class Reader_decorator<T extends Comparable<T> & IDAccess> extends Abstract_file_reader<T> {
    Abstract_file_reader<T> reader;


    public Reader_decorator(String fileName, LoadDescription<T> load_description, Abstract_file_reader<T> reader) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, load_description);
        this.reader = reader;
    }
}
