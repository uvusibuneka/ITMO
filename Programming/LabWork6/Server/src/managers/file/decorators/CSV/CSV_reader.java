package managers.file.decorators.CSV;

import managers.file.CSV_savable;
import managers.file.decorators.Reader_decorator;
import common.Collection;
import common.IDAccess;
import managers.file.Abstract_file_reader;
import common.descriptions.LoadDescription;
import managers.BaseTextReceiver;
import result.Result;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CSV_reader<T extends Comparable<T> & IDAccess & CSV_savable> extends Reader_decorator<T> {
    public CSV_reader(String fileName, LoadDescription<T> load_description, Abstract_file_reader<T> reader, Collection<T> collection) throws FileNotFoundException, NullPointerException, SecurityException {
        super(fileName, load_description, reader, collection);
    }

    @Override
    public Result<Collection<T>> read() {
        BufferedReader buffered_reader = null;
        String line;
        try {
            buffered_reader = new BufferedReader(new FileReader(file));
            while (true) {
                line = buffered_reader.readLine();
                if (!(line == null || line.equals(""))) {
                    CSV_Loader loader = new CSV_Loader(new BaseTextReceiver() {
                        @Override
                        public void print(String message) {
                        }

                        @Override
                        public void println(String message) {
                        }
                    }, line);
                    Result<?> res = collection.add(loader.enter(load_description).getValue());
                    if (!res.isSuccess()) {
                        buffered_reader.close();
                        return Result.failure(res.getError().get(), res.getMessage());
                    }
                } else {
                    break;
                }
            }
            buffered_reader.close();
            return Result.success(collection);
        } catch (IndexOutOfBoundsException e) {
            return Result.failure(e, "Файл с коллекцией не соответствует структуре хранимых объектов");
        } catch (FileNotFoundException e) {
            return Result.failure(e, "Ошибка при открытии файла");
        } catch (Exception e) {
            return Result.failure(e, "Ошибка валидации объекта");
        }
    }
}
