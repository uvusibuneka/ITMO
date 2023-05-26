package collection_file_managers.decorators.CSV;

import collection_file_managers.decorators.Writer_decorator;
import common.Collection;
import common.IDAccess;
import collection_file_managers.Abstract_file_writer;
import descriptions.LoadDescription;
import result.Result;

import java.io.IOException;

public class CSV_writer<T extends Comparable<T> & IDAccess & CSV_savable> extends Writer_decorator<T> {
    public CSV_writer(String fileName, Abstract_file_writer<T> writer) throws IOException, NullPointerException, SecurityException {
        super(fileName, writer);
    }

    @Override
    public void write() throws Exception {
        for (T i: collection.getCollection()){
            Result<String> csv_row = i.toCSV();
            if (csv_row.isSuccess())
                buffered_writer.write(csv_row.getValue().get()+"\n");
            else
                throw new Exception(csv_row.getMessage());
        }
    }

}
