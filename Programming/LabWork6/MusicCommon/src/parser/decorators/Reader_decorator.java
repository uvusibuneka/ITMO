package parser.decorators;

import common.IDAccess;
import parser.Abstract_file_reader;

import java.io.FileNotFoundException;

public abstract class Reader_decorator<T extends Comparable<T> & IDAccess> extends Abstract_file_reader<T> {
    Abstract_file_reader<T> reader;


    public Reader_decorator(String fileName, Abstract_file_reader<T> reader) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName);
        this.reader = reader;
    }
}
