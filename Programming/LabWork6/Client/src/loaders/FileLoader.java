package loaders;

import descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.BaseTextReceiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileLoader extends AbstractLoader {
    private String filename;

    private BufferedReader reader;

    public FileLoader(BaseTextReceiver textReceiver, String fileName) {
        super(textReceiver);
    }

    public FileLoader(String fileName){
        super(null);
        this.filename = fileName;
        File file = new File(fileName);
        if(!file.exists()){
            throw new RuntimeException("File not found!");
        }
        if(!file.canRead()){
            throw new RuntimeException("File can't be read!");
        }
        if(!file.isFile()){
            throw new RuntimeException("File is not a file!");
        }
        try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e){
            throw new RuntimeException("Error while opening file!");
        }

    }

    @Override
    public <T extends LoadDescription<Enum>> T enterEnum(T t) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (true) {
        try {
            return (T) t.setValue(Enum.valueOf((Class<Enum>) t.getType(), s));
        } catch (Exception e) {
            textReceiver.println(e.getMessage());
        }
    }
    }

    @Override
    public <T extends LoadDescription<Number>> T enterWrapper(T t) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                return (T)t.setValue(parseNumber(s, (Class<? extends Number>)t.getType()));
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> loadDescription) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                return loadDescription.setValue(s);
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    @Override
    public <T> T parseComposite(String s, Class<T> aClass) {
        return null;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
