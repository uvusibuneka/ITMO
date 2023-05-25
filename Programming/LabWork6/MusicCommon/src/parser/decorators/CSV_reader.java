package parser.decorators;

import common.Collection;
import common.IDAccess;
import parser.Abstract_file_reader;

import java.io.FileNotFoundException;

public class CSV_reader <T extends Comparable<T> & IDAccess> extends Reader_decorator<T>{
    public CSV_reader(String fileName, Abstract_file_reader<T> reader) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, reader);
    }

    @Override
    public Collection<T> read() {
        return null;
    }
}
