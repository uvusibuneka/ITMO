package specialDescriptions;

import callers.specialClientCaller;
import descriptions.CommandDescription;
import descriptions.LoadDescription;
import loaders.FileLoader;
import modules.CallableManager;
import modules.InteractiveMode;
import modules.ObjectSender;
import result.Result;

import java.io.IOException;
import java.util.List;

public class ExecuteScriptDescription extends CommandDescription {
    public ExecuteScriptDescription(CallableManager callableManager, ObjectSender objectSender) {
        super("execute_script");

        this.setCaller(new specialClientCaller(() -> {
            String path = (String) this.getArguments().get(0).getValue();
            FileLoader fileLoader = new FileLoader(path);
            m:while(true) {
                String s = null;
                try {
                    s = (String) fileLoader.enterString(new LoadDescription<>(String.class)).getValue();
                } catch (IOException e) {
                    break m;
                }
                CommandDescription commandDescription = fileLoader.parseCommand(InteractiveMode.getInstance().getCommandDescriptionMap(), s);
                callableManager.add(commandDescription.getCaller());
            }
            List<Result<?>> result = callableManager.callAll();
        }, this, objectSender));
        }


}
