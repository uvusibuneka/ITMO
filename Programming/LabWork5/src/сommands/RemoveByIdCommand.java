/**

 Команда для удаления элемента коллекции по его id.
 */
package сommands;


import result.Result;
import managers.Receiver;

public class RemoveByIdCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.removeById();
    }
}