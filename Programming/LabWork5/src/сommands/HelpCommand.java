/**
 *
 * Команда для вывода справки по всем доступным командам.
 *
 */
package сommands;

import result.Result;
import managers.Receiver;

import java.io.BufferedReader;
import java.util.Map;

public class HelpCommand implements Command {

    private final Map<String, Command> commands;

    private BufferedReader primaryReader;


    public HelpCommand(Map<String, Command> commands){
        this.commands = commands;
    }



    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.printHelpInfo(commands);
    }
}
