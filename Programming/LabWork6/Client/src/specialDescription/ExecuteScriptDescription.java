package specialDescription;

import callers.SpecialClientCaller;
import common.Collection;
import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import loaders.FileLoader;
import modules.CallableManager;
import modules.InteractiveMode;
import modules.ObjectSender;
import result.Result;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ExecuteScriptDescription extends CommandDescription {
    private static Deque<String> fileNameStack = new ArrayDeque<>();


    public ExecuteScriptDescription(CallableManager callableManager, ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("execute_script", "Выполнить скрипт из указанного файла.");
        this.setOneLineArguments(List.of(new LoadDescription<String>(String.class)));
        this.setCaller(new SpecialClientCaller(() -> {
            return null;
        }, this, objectSender) {
            @Override
            public void call() throws Exception{

                String path = (String) interactiveMode.getCommandDescriptionMap().get("execute_script").getOneLineArguments().get(0).getValue();
                System.out.println(path);
                FileLoader fileLoader = new FileLoader(path);
                if (fileNameStack.contains(path)) {
                    fileNameStack.clear();
                    throw new RuntimeException("Recursion detected");
                }
                fileNameStack.push(path);


                while (fileLoader.hasNext()) {
                    String s;
                    CommandDescription commandDescription;
                    try {
                        s = fileLoader.enter(new LoadDescription<String>(String.class)).getValue();
                        if (s == null)
                            break;
                        System.out.println(s);
                        commandDescription = fileLoader.parseCommand(interactiveMode.getCommandDescriptionMap(), s);
                    } catch (Exception e) {
                        fileNameStack.clear();
                        throw new Exception("Bad script. " + (e.getMessage() != null ? e.getMessage() : ""));
                    }
                    if (commandDescription.getName().equals("execute_script"))
                        commandDescription.getCaller().call();
                    else if (interactiveMode.isSpecial(commandDescription.getName()))
                        callableManager.addSpecial(commandDescription.getCaller());
                    else
                        callableManager.add(commandDescription.getCaller());
                }

                if (fileNameStack.size() == 1) {
                    List<Result<?>> results = null;
                    try {
                        results = callableManager.callAll();
                    } catch (InterruptedException e) {
                        fileNameStack.clear();
                        throw new RuntimeException(e);
                    }
                    results.forEach((res) -> {
                        if (res.getValue().isPresent()) {
                            Object object = res.getValue().get();
                            if (object instanceof Collection)
                                for (Object o : ((Collection<?>) object).getCollection())
                                    System.out.println(o.toString());
                            else System.out.println(object.toString());
                        }
                        System.out.println(res.getMessage());
                    });
                    callableManager.clear();
                }
                fileNameStack.pop();
            }
        });
    }
}
