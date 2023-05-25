package parser.decorators;

import common.IDAccess;
import parser.Abstract_file_writer;

import java.io.FileNotFoundException;

public class CSV_writer<T extends Comparable<T> & IDAccess> extends Writer_decorator<T>{
    public CSV_writer(String fileName, Abstract_file_writer<T> writer) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, writer);
    }

    @Override
    public void write() {

    }
}
