package managers.file.decorators;

import common.Collection;
import common.IDAccess;
import managers.file.Abstract_file_reader;
import common.descriptions.LoadDescription;

import java.io.FileNotFoundException;

public abstract class Reader_decorator<T extends Comparable<T> & IDAccess> extends Abstract_file_reader<T> {
    Abstract_file_reader<T> reader;


    public Reader_decorator(String fileName, LoadDescription<T> load_description, Abstract_file_reader<T> reader, Collection<T> collection) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, load_description, collection);
        this.reader = reader;
    }
}
