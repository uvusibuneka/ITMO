package parser;

import common.Collection;
import common.IDAccess;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class Abstract_file_writer<T extends Comparable<T> & IDAccess> {
    protected Collection<T> collection;
    protected File file;
    public Abstract_file_writer(String fileName) throws FileNotFoundException, NullPointerException, SecurityException {
        if (fileName == null) {
            throw new NullPointerException("FILE_NAME is not set");
        }

        file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist");
        }

        if (!file.canWrite()) {
            throw new SecurityException("File " + fileName + " is not writable");
        }
    }


    public abstract void write();
}
