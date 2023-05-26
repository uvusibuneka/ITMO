package collection_file_managers;

import collection_file_managers.decorators.CSV.CSV_savable;
import common.Collection;
import common.IDAccess;
import descriptions.LoadDescription;
import result.Result;

import java.io.FileNotFoundException;

public class File_reader<T extends Comparable<T> &IDAccess & CSV_savable> extends Abstract_file_reader<T>{
    public File_reader(String fileName, LoadDescription<T> load_description, Collection<T> collection) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, load_description, collection);
    }

    @Override
    public Result<Collection<T>> read() {
        System.out.println("Я ничего не умею(( Есть парни из группировки декораторов, каждый из них работает со своим файлом. Обратись к ним.");
        return null;
    }
}
