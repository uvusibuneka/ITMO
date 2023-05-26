package managers.file.decorators.CSV;

import managers.file.CSV_savable;
import managers.file.decorators.Writer_decorator;
import common.IDAccess;
import managers.file.Abstract_file_writer;
import result.Result;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_writer<T extends Comparable<T> & IDAccess & CSV_savable> extends Writer_decorator<T> {
    public CSV_writer(String fileName, Abstract_file_writer<T> writer) throws IOException, NullPointerException, SecurityException {
        super(fileName, writer);
    }

    @Override
    public void write() throws Exception {
        BufferedWriter buffered_writer = new BufferedWriter(new FileWriter(file));
        for (T i: collection.getCollection()){
            Result<String> csv_row = i.toCSV();
            if (csv_row.isSuccess())
                buffered_writer.write(csv_row.getValue().get()+"\n");
            else
                throw new Exception(csv_row.getMessage());
        }
        buffered_writer.close();
    }

}
