package common.descriptions;

import caller.Caller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandDescription  implements Serializable, Cloneable{
    private String name;
    private String description;
    private List<LoadDescription<?>> oneLineArguments;
    private List<LoadDescription<?>> arguments;
    protected transient Caller caller;

    public CommandDescription(String name, String description, List<LoadDescription<?>> oneLineArguments) {
        this(name, description, oneLineArguments, null);
    }

    public CommandDescription(String name, String description) {
        this(name, description, null, null);
    }

    public CommandDescription(String name, String description, List<LoadDescription<?>> oneLineArguments, List<LoadDescription<?>> arguments) {
        this.name = name;
        this.description = description;
        this.oneLineArguments = oneLineArguments;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
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

    @Override
    public CommandDescription clone() throws CloneNotSupportedException {
        return (CommandDescription) super.clone();
    }

}
