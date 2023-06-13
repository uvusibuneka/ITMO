package loaders;

import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.BaseTextReceiver;

import javax.imageio.IIOException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
    public <T extends LoadDescription<?>> T enterDate(T t) {
        try {
            return (T) t.setValue(parse(reader.readLine(), (Class<?>)t.getType()));
        } catch (Exception e) {
            textReceiver.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends LoadDescription<Enum>> T enterEnum(T t) {
        try {
            return (T) t.setValue(Enum.valueOf((Class<Enum>) t.getType(), reader.readLine()));
        } catch (Exception e) {
            textReceiver.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends LoadDescription<?>> T enterWrapper(T t) {
        try {
            return (T)t.setValue(parse(reader.readLine(), (Class<?>)t.getType()));
        } catch (Exception e) {
            textReceiver.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> loadDescription) {
        try {
            return loadDescription.setValue(reader.readLine());
        } catch (Exception e) {
            textReceiver.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public CommandDescription parseCommand(Map<String, CommandDescription> commandDescriptionMap, String command) {
        List<String> commandParts = List.of(command.split(" "));
        if (commandParts.size() == 0) {
            throw new RuntimeException("Command is empty!");
        }
        if (commandDescriptionMap.containsKey(commandParts.get(0))) {
            CommandDescription commandDescription = null;
            try {
                commandDescription = commandDescriptionMap.get(commandParts.get(0)).clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            if (commandDescription.getOneLineArguments().size() != commandParts.size() - 1) {
                throw new RuntimeException("Wrong number of arguments!");
            }
            if(commandDescription.getOneLineArguments().size() != commandParts.size()){
                throw new RuntimeException("Wrong number of arguments!");
            }
            CommandDescription finalCommandDescription = commandDescription;
            IntStream.range(0, commandDescription.getOneLineArguments().size())
                    .forEach(i -> finalCommandDescription.getOneLineArguments()
                            .get(i)
                            .setValue(
                                    parse(commandParts.get(i),
                                            finalCommandDescription.getOneLineArguments()
                                                    .get(i)
                                                    .getType()
                                    )
                            ));
            commandDescription.getArguments()
                    .stream()
                    .forEach(loadDescription -> {
                        loadDescription = enter(loadDescription);
                    });
            return commandDescription;
        } else {
            throw new RuntimeException("Unknown command!");
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
