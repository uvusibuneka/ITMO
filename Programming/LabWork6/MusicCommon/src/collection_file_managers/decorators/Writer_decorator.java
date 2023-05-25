package collection_file_managers.decorators;

import common.IDAccess;
import collection_file_managers.Abstract_file_writer;

import java.io.FileNotFoundException;

public abstract class Writer_decorator<T extends Comparable<T> & IDAccess> extends Abstract_file_writer<T>{
    Abstract_file_writer<T> writer;

    public Writer_decorator(String fileName, Abstract_file_writer<T> writer) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName);
        this.writer = writer;
    }
}
