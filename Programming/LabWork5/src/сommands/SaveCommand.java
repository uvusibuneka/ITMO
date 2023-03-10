/**

 Команда для сохранения коллекции музыкальных групп в файл.
 */
package сommands;

import result.Result;
import managers.Receiver;

public class SaveCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.saveCollection();
    }
}