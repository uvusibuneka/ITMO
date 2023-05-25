package parser;

import common.Collection;
import common.IDAccess;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class Abstract_file_reader<T extends Comparable<T> & IDAccess> {
    protected Collection<T> collection;
    protected File file;

    public Abstract_file_reader(String fileName) throws FileNotFoundException, NullPointerException, SecurityException {
        if (fileName == null) {
            throw new NullPointerException("FILE_NAME is not set");
        }

        file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist");
        }

        if (!file.canRead()) {
            throw new SecurityException("File " + fileName + " is not readable");
        }
    }

    public abstract Collection<T> read();
}
