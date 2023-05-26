package collection_file_managers;

import collection_file_managers.decorators.CSV.CSV_savable;
import common.Collection;
import common.IDAccess;
import descriptions.LoadDescription;

import java.io.*;

public abstract class Abstract_file_writer<T extends Comparable<T> & IDAccess & CSV_savable> {
    protected Collection<T> collection;
    protected BufferedWriter buffered_writer;
    protected LoadDescription<T> load_description;
    public Abstract_file_writer(String fileName, LoadDescription<T> load_description) throws IOException, NullPointerException, SecurityException {
        this.load_description = load_description;

        if (fileName == null) {
            throw new NullPointerException("FILE_NAME is not set");
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist");
        }

        if (!file.canWrite()) {
            throw new SecurityException("File " + fileName + " is not writable");
        }

        buffered_writer = new BufferedWriter(new FileWriter(file));
    }


    public abstract void write() throws Exception;
}
