/**

 Класс команды "max_by_best_album", выводящей элемент коллекции с наибольшим значением поля bestAlbum.
 */
package сommands;

import Result.Result;
import managers.Receiver;

public class MaxByBestAlbumCommand implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.maxByBestAlbum();
    }
}