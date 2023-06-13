package specialDescription;

import callers.SpecialClientCaller;
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
        super("execute_script","Выполнить скрипт из указанного файла.");
        this.setOneLineArguments(List.of(new LoadDescription<String>(String.class)));
        this.setCaller(new SpecialClientCaller(() -> {
            System.out.println(super.toString());
            String path = (String) interactiveMode.getCommandDescriptionMap().get("execute_script").getOneLineArguments().get(0).getValue();
            FileLoader fileLoader = new FileLoader(path);
            if (fileNameStack.contains(path)) {
                throw new RuntimeException("Recursion detected");
            }
            fileNameStack.push(path);
            while(fileLoader.hasNext()) {
                String s;
                try {
                    s = fileLoader.enter(new LoadDescription<String>(String.class)).getValue();
                    if(s == null)
                        break;
                    System.out.println(s);
                } catch (Exception e) {
                    break;
                }
                CommandDescription commandDescription = fileLoader.parseCommand(interactiveMode.getCommandDescriptionMap(), s);
                callableManager.add(commandDescription.getCaller());
            }
            List<Result<?>> result = null;
            try {
                result = callableManager.callAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result.stream()
                    .map(Result::getMessage)
                    .forEach(System.out::println);
            callableManager.clear();
            fileNameStack.pop();
            return null;
        }, this, objectSender));
        }

}
