package loaders;

import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import managers.AbstractLoader;
import managers.BaseTextReceiver;
import parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ConsoleLoader extends AbstractLoader {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleLoader(BaseTextReceiver textReceiver) {
        super(textReceiver);
        parser = new Parser();
    }

    @Override
    public <T extends LoadDescription<?>> T enterDate(T t) {
        String s = null;
        while (true) {
            try {
                s = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                return (T) t.setValue(parser.parse(s, t.getType()));
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    public CommandDescription parseCommand(Map<String, CommandDescription> commandDescriptionMap, String command) {
        List<String> commandParts = List.of(command.split(" "));
        if (commandParts.size() == 0) {
            throw new RuntimeException("Command is empty!");
        }
        if (commandDescriptionMap.containsKey(commandParts.get(0))) {
            CommandDescription commandDescription;
            try {
                commandDescription = commandDescriptionMap.get(commandParts.get(0)).clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            if(commandDescription.getOneLineArguments() == null) {
                if(commandParts.size() != 1) {
                    throw new RuntimeException("Wrong number of arguments!");
                }
            }else {
                if (commandDescription.getOneLineArguments().size() != commandParts.size() - 1) {
                    throw new RuntimeException("Wrong number of arguments!");
                }
            }
            if(commandDescription.getOneLineArguments() != null) {
                IntStream.range(0, commandDescription.getOneLineArguments().size())
                        .forEach(i -> commandDescription.getOneLineArguments()
                                .get(i)
                                .setValue(
                                        parse(commandParts.get(i+1),
                                                commandDescription.getOneLineArguments()
                                                        .get(i)
                                                        .getType()
                                        )
                                ));
            }
            if(commandDescription.getArguments() == null) {
                return commandDescription;
            }


            commandDescription.getArguments()
                    .forEach(loadDescription -> {
                        enterWithMessage("Enter arguments of command according to description \"" + loadDescription.getDescription() +  "\":\n", loadDescription);
                    });
            return commandDescription;
        } else {
            throw new RuntimeException("Unknown command!");
        }
    }



    public <T extends LoadDescription<Enum>> T enterEnum(String s, T t, BaseTextReceiver baseTextReceiver) {
        baseTextReceiver.print(s);
        String line = null;
        while (true) {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try {
                return (T) t.setValue(Enum.valueOf(t.getType(), line));
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    @Override
    public <T extends LoadDescription<?>> T enterWrapper(T t) {
        String s = null;
        while (true) {
        try {
            s = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try {
                return (T)t.setValue(parse(s, t.getType()));
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    @Override
    public LoadDescription<String> enterString(LoadDescription<String> description) {
        String s;
        while (true){
        try {
            s = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try {
                return description.setValue(s);
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }

    @Override
    public <T extends LoadDescription<Enum>> T enterEnum(T t) {
        String s;
        while (true) {
        try {
            s = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try {
                return (T) t.setValue(parse(s, (Class<Enum>) t.getType()));
            } catch (Exception e) {
                textReceiver.println(e.getMessage());
            }
        }
    }


    protected <T extends LoadDescription<?>> T enterComposite(T description) {
        try {
            var res = super.enterComposite(description);
            return res;
        } catch (Exception e) {
            textReceiver.println(String.valueOf(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}