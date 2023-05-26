package collection_file_managers;

import collection_file_managers.decorators.CSV.CSV_savable;
import common.Collection;
import common.IDAccess;
import descriptions.LoadDescription;
import result.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Abstract_file_reader<T extends Comparable<T> & IDAccess & CSV_savable> {
    protected Collection<T> collection;
    protected Scanner scanner;
    protected LoadDescription<T> load_description;

    public Abstract_file_reader(String fileName, LoadDescription<T> load_description, Collection<T> collection) throws FileNotFoundException, NullPointerException, SecurityException {
        this.load_description = load_description;
        this.collection = collection;

        if (fileName == null) {
            throw new NullPointerException("FILE_NAME is not set");
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist");
        }

        if (!file.canRead()) {
            throw new SecurityException("File " + fileName + " is not readable");
        }

        scanner = new Scanner(file);
    }

    public abstract Result<Collection<T>> read();
}
