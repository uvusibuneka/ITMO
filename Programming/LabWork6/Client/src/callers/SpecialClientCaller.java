package callers;

import common.descriptions.CommandDescription;
import modules.ObjectSender;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpecialClientCaller extends ServerCommandCaller {
    protected Supplier<Void> supplier;

    public SpecialClientCaller(Supplier supplier, CommandDescription commandDescription, ObjectSender objectSender){
        super(commandDescription, objectSender);
        this.supplier = supplier;
    }

    @Override
    public void call() throws Exception {
        supplier.get();
    }

}
