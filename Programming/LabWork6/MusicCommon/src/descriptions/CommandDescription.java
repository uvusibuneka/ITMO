package descriptions;

import caller.Caller;
import java.io.Serializable;
import java.util.List;

public class CommandDescription  implements Serializable{
    private String name;
    private List<LoadDescription<?>> oneLineArguments;
    private List<LoadDescription<?>> arguments;
    protected transient Caller caller;

    public CommandDescription(String name, List<LoadDescription<?>> oneLineArguments) {
        this(name, oneLineArguments, null);
    }

    public CommandDescription(String name) {
        this(name, null, null);
    }

    public CommandDescription(String name, List<LoadDescription<?>> oneLineArguments, List<LoadDescription<?>> arguments) {
        this.name = name;
        this.oneLineArguments = oneLineArguments;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<LoadDescription<?>> getOneLineArguments() {
        return oneLineArguments;
    }

    public List<LoadDescription<?>> getArguments() {
        return arguments;
    }

    public void setArguments(List<LoadDescription<?>> arguments) {
        this.arguments = arguments;
    }

    public void setOneLineArguments(List<LoadDescription<?>> oneLineArguments) {
        this.oneLineArguments = oneLineArguments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Caller getCaller(){
        return caller;
    }

    public void setCaller(Caller caller){
        this.caller = caller;
    }

    public void call() {
        caller.call();
    }

}
