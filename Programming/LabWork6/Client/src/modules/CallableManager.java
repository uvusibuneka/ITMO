package modules;

import caller.Caller;

import result.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallableManager {
    private List<Caller> callers = new ArrayList<>();
    private RequestHandler requestHandler;
    private List<Caller> specialCallers = new ArrayList<>();
    public CallableManager(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void add(Caller caller) {
        callers.add(caller);
    }

    public void addSpecial(Caller caller){
        if(!specialCallers.contains(caller))
            specialCallers.add(caller);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public List<Result<?>> callAll() {
        List<Result<?>> results = new ArrayList<>();
        for(Caller caller : callers){
            try {
                if(specialCallers.contains(caller)) {
                    try{
                        caller.call();
                        results.add(Result.success(null));
                    }catch (Exception e){
                        results.add(Result.failure(e));
                    }
                    continue;
                }
                caller.call();
                Result<DatagramPacket> packet = requestHandler.receivePacketWithTimeout();
                if (!packet.isSuccess()){
                    results.add(Result.failure(packet.getError().get()));
                    continue;
                }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getValue().get().getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Result<?> result = (Result<?>) objectInputStream.readObject();
                results.add(result);
            }catch (IOException | ClassNotFoundException e){
                results.add(Result.failure(e));
            }
        }
        return results;
    }

    public void clear() {
        callers.clear();
    }
}

