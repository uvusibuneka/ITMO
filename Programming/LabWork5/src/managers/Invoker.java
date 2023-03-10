/**

 Invoker - класс, который управляет выполнением команд.
 Хранит список всех доступных команд и их названий, а также историю выполненных команд и стек выполненных скриптов.
 При вызове executeCommand вызывает соответствующую команду.

 */
package managers;

import result.Result;
import сommands.*;

import java.util.*;

public class Invoker {

    private Map<String, Command> commands;
    private Deque<String> history;
    private Stack<String> scriptStack; // добавленное поле

    private Loader loader;

    public Invoker(Loader loader) {
        this.loader = loader;
        commands = new HashMap<>();
        history = new LinkedList<>();
        scriptStack = new Stack<>();
        register("add", new AddCommand());
        register("info", new InfoCommand());
        register("show", new ShowCommand());
        register("clear", new ClearCommand());
        register("help", new HelpCommand(commands));
        register("update", new UpdateCommand());
        register("remove_by_id", new RemoveByIdCommand());
        register("execute_script", new ExecuteScriptCommand(this));
        register("exit", new ExitCommand());
        register("add_if_max", new AddIfMaxCommand());
        register("remove_greater", new RemoveGreaterCommand());
        register("history", new HistoryCommand(history));
        register("max_by_best_album", new MaxByBestAlbumCommand());
        register("count_by_best_album", new CountByBestAlbum());
        register("filter_by_best_album", new FilterByBestAlbum());
        register("save", new SaveCommand());
    }

    public void register(String name, Command command) {
        commands.put(name, command);
    }

    public Result<Void> executeCommand(String name, Receiver receiver) {
        try {
            if (commands.containsKey(name)) {
                Command command = commands.get(name);
                Result<Void> executeResult = command.execute(receiver);
                try {
                    if (!executeResult.isSuccess()) {
                        return Result.failure(executeResult.getError().get(), executeResult.getMessage());
                    }
                } catch (Exception e) {
                    return Result.failure(e, "Error while executing command");
                }
                history.offerLast(name);
                if (history.size() > 6) {
                    history.pollFirst();
                }
                return Result.success(null);
            } else {
                return Result.failure(new Exception("Command not found"), "Error while executing command");
            }
        } catch (Exception e) {
            return Result.failure(e, "Error while executing command");
        }
    }

    public void printHistory() {
        for (String command : history) {
            System.out.println(command);
        }
    }

    public boolean addExecutedScript(String filename) {
        if(scriptStack.contains(filename))
            return false;
        else {
            scriptStack.push(filename);
            return true;
        }
    }

    public void removeExecutedScript() {
        if (!scriptStack.empty()) {
            scriptStack.pop();
        }
    }

    public void printStack() {
        for (var s: scriptStack) {
            System.out.println(s.toString());
        }
    }
}
