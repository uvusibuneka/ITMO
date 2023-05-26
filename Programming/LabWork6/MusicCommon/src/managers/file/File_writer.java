package managers.file;

import common.IDAccess;

import java.io.IOException;

public class File_writer<T extends Comparable<T> & IDAccess> extends Abstract_file_writer<T>{
    public File_writer(String fileName) throws IOException, NullPointerException, SecurityException {
        super(fileName);
    }

    @Override
    public void write() {
        System.out.println("Я ничего не умею(( Есть парни из группировки декораторов, каждый из них работает со своим файлом. Обратись к ним.");
    }
}
