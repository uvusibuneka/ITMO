package common;

import caller.Callable;
import caller.Caller;

import java.util.List;

public class CommandDescription implements Callable {
    private String name;
    private List<String> oneLineArguments;
    private List<Object> ObjectArgument;

    private Caller caller;

    public CommandDescription(String name, List<String> oneLineArguments, List<Object> ObjectArgument) {
        this.name = name;
        this.oneLineArguments = oneLineArguments;
        this.ObjectArgument = ObjectArgument;
    }

    public class Builder {
        private String name;
        private List<String> oneLineArguments;
        private List<Object> ObjectArgument;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOneLineArguments(List<String> oneLineArguments) {
            this.oneLineArguments = oneLineArguments;
            return this;
        }

        public Builder setObjectArgument(List<Object> ObjectArgument) {
            this.ObjectArgument = ObjectArgument;
            return this;
        }

        public Builder setCaller(Caller caller) {
            CommandDescription.this.caller = caller;
            return this;
        }
        public CommandDescription build() {
            return new CommandDescription(name, oneLineArguments, ObjectArgument);
        }

    }

    public Caller getCaller() {
        return caller;
    }

    public void call() {
        caller.call();
    }
    public String getName() {
        return name;
    }

    public List<?> getOneLineArguments() {
        return oneLineArguments;
    }

    public Object getObjectArgument() {
        return ObjectArgument;
    }

}
