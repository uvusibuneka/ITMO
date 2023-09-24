package result;

import common.MusicBand;

public class ClearWarning extends UpdateWarning{
    protected ClearWarning() {
        super(null, 0);
    }


    public static ClearWarning warning() {
        return new ClearWarning();
    }
}
