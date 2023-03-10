/**

 Класс RemoveGreaterCommand реализует интерфейс Command и отвечает за удаление из коллекции всех элементов, которые больше заданного.
 */
package сommands;

import Result.Result;
import managers.Receiver;

public class RemoveGreaterCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.removeGreaterCommand();
    }
}