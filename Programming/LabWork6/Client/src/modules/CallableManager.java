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

    public CallableManager(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void add(Caller caller) {
        callers.add(caller);
    }

    public List<Result<?>> callAll() {
        List<Result<?>> results = new ArrayList<>();
        for(Caller caller : callers){
            try {
                caller.call();
                DatagramPacket packet = requestHandler.receivePacketWithTimeout();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
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

