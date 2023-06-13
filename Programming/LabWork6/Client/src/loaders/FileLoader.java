package loaders;

import callers.ServerCommandCaller;
import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.AbstractParser;
import managers.BaseTextReceiver;
import modules.InteractiveMode;

import javax.imageio.IIOException;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class FileLoader extends AbstractLoader {
    private String filename;

    private boolean hasNext = true;
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
        parser = new AbstractParser() {
            @Override
            public <T> T parseComposite(String s, Class<T> type) {
                return null;
            }
        };
    }

    public boolean hasNext(){
        return hasNext;
    }
    @Override
    public <T extends LoadDescription<?>> T enterDate(T t) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        t.setField(parser.parse(s, t.getType()));
        return t;
    }

    @Override
    public <T extends LoadDescription<Enum>> T enterEnum(T t) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        t.setField(parser.parseEnum(s, t.getType()));
        return t;
    }

    @Override
    public <T extends LoadDescription<?>> T enterWrapper(T t) {
        String s = null;
        try {
            s = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        t.setField(parser.parseWrapper(s, t.getType()));
        return t;
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> loadDescription) {
        String s = null;
        try {
            s = reader.readLine();
            if(s == null)
                hasNext = false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (loadDescription.getFieldOfDescriptionSetter() != null)
            loadDescription.setField(s);
        else
            loadDescription.setValue(s);

        return loadDescription;
    }

    @Override
    public  <T extends LoadDescription<?>> T enterComposite(T description) {
        description.getFields().forEach(this::enter);
        description.build();
        if (description.getFieldOfDescriptionSetter() != null)
            description.setField(description.getValue());
        return description;
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
            if (commandDescription.getOneLineArguments() != null) {
                if (commandDescription.getOneLineArguments().size() != commandParts.size() - 1) {
                    throw new RuntimeException("Wrong number of arguments!");
                }
                CommandDescription finalCommandDescription = commandDescription;
                IntStream.range(0, commandDescription.getOneLineArguments().size())
                        .forEach(i -> finalCommandDescription.getOneLineArguments()
                                .get(i)
                                .setValue(
                                        parse(commandParts.get(i+1),
                                                finalCommandDescription.getOneLineArguments()
                                                        .get(i)
                                                        .getType()
                                        )
                                ));
            }
            if (commandDescription.getArguments() != null) {
                commandDescription.getArguments()
                        .forEach(this::enter);
            }
            if(InteractiveMode.getObject().isSpecial(commandDescription.getName())){
                commandDescription.setCaller(InteractiveMode.getObject().getSpecialCommands().get(commandDescription.getName()).getCaller());
            }else{
                commandDescription.setCaller(new ServerCommandCaller(commandDescription, InteractiveMode.getObject().getObjectSender()));
            }
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

    public boolean isHasNext() {
        return hasNext;
    }
}
