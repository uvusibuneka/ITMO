package parser;

import common.Collection;
import common.IDAccess;

import java.io.FileNotFoundException;

public class File_reader<T extends Comparable<T> &IDAccess> extends Abstract_file_reader<T>{
    public File_reader(String fileName) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName);
    }

    @Override
    public Collection<T> read() {
        return null;
    }
}
