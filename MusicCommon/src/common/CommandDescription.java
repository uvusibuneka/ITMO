package common;

import java.util.List;

public class CommandDescription {
    private String name;
    private List<?> oneLineArguments;
    private Object ObjectArgument;

    public CommandDescription(String name, List<?> oneLineArguments, Object ObjectArgument) {
        this.name = name;
        this.oneLineArguments = oneLineArguments;
        this.ObjectArgument = ObjectArgument;
    }

    public class Builder {
        private String name;
        private List<?> oneLineArguments;
        private Object ObjectArgument;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOneLineArguments(List<?> oneLineArguments) {
            this.oneLineArguments = oneLineArguments;
            return this;
        }

        public Builder setObjectArgument(Object ObjectArgument) {
            this.ObjectArgument = ObjectArgument;
            return this;
        }

        public CommandDescription build() {
            return new CommandDescription(name, oneLineArguments, ObjectArgument);
        }
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
