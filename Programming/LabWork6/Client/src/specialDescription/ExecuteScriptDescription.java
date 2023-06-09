package specialDescription;

import callers.specialClientCaller;
import common.descriptions.CommandDescription;
import common.descriptions.LoadDescription;
import loaders.FileLoader;
import modules.CallableManager;
import modules.InteractiveMode;
import modules.ObjectSender;
import result.Result;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ExecuteScriptDescription extends CommandDescription {
    private transient static Deque<String> fileNameStack = new ArrayDeque<>();

    public ExecuteScriptDescription(CallableManager callableManager, ObjectSender objectSender, InteractiveMode interactiveMode) {
        super("execute_script");

        this.setCaller(new specialClientCaller(() -> {
            String path = (String) this.getArguments().get(0).getValue();
            FileLoader fileLoader = new FileLoader(path);
            if (fileNameStack.contains(path)) {
                throw new RuntimeException("Recursion detected");
            }
            fileNameStack.push(path);
            while(true) {
                String s;
                try {
                    s = fileLoader.enterString(new LoadDescription<>(String.class)).getValue();
                } catch (Exception e) {
                    break;
                }
                CommandDescription commandDescription = fileLoader.parseCommand(interactiveMode.getCommandDescriptionMap(), s);
                callableManager.add(commandDescription.getCaller());
            }
            List<Result<?>> result = callableManager.callAll();
            result.stream()
                    .map(Result::getMessage)
                    .forEach(System.out::println);
            callableManager.clear();
            fileNameStack.pop();
        }, this, objectSender));
        }


}
