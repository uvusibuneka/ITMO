package collection_file_managers.decorators.CSV;

import result.Result;

public interface CSV_savable {
    public Result<String> toCSV();
}
