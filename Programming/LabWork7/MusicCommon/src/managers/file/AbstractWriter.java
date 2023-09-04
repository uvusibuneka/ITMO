package managers.file;

import common.Collection;
import common.IDAccess;

import java.io.*;

public abstract class AbstractWriter<T extends Comparable<T> & IDAccess> implements Serializable {
    protected Collection<T> collection;
    protected String destination;
    public AbstractWriter(String destination) throws NullPointerException {

        if (destination == null) {
            throw new NullPointerException("FILE_NAME is not set");
        }

        this.destination = destination;
    }

    public void setCollection(Collection<T> collection){
        this.collection = collection;
    }
    public abstract void write() throws Exception;
}
