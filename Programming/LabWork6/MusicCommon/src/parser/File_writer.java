package parser;

import common.IDAccess;

import java.io.FileNotFoundException;

public class File_writer<T extends Comparable<T> & IDAccess> extends Abstract_file_writer<T>{
    public File_writer(String fileName) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName);
    }

    @Override
    public void write() {

    }
}
