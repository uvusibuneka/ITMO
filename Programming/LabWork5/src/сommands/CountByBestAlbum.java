package сommands;

import result.Result;
import managers.Receiver;

public class CountByBestAlbum implements Command {

    @Override
    public Result<Void> execute(Receiver receiver) {
        return receiver.countByBestAlbum();
    }
}
