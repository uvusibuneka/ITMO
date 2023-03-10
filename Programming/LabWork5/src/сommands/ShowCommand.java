/**

 Класс представляет команду "Show" для отображения всех объектов MusicBand в коллекции.
 */
package сommands;

import Result.Result;
import managers.Receiver;

public class ShowCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.showElementsOfCollection();
    }
}