package collection_file_managers.decorators;

import collection_file_managers.decorators.CSV.CSV_savable;
import common.IDAccess;
import collection_file_managers.Abstract_file_writer;
import descriptions.LoadDescription;

import java.io.IOException;

public abstract class Writer_decorator<T extends Comparable<T> & IDAccess & CSV_savable> extends Abstract_file_writer<T>{
    Abstract_file_writer<T> writer;

    public Writer_decorator(String fileName, Abstract_file_writer<T> writer, LoadDescription<T> load_description) throws IOException, NullPointerException, SecurityException {
        super(fileName, load_description);
        this.writer = writer;
    }
}
