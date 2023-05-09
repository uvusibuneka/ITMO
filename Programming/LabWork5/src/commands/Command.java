package commands;

import receivers.*;
import result.Result;

public abstract class Command {

    /**

     Constructor for creating a command object.
     @param description the description of the command
     */
    private String description;
    public Command(String description) {
        this.description = description;
    }

    /**

     A method that will be called when executing the command.
     @param receiver an object that will execute the command
     @return the result of executing the command
     */
    public abstract Result<Void> execute(Receiver receiver);

    /**

     A method that returns the description of the command.
     @return the description of the command
     */
    public String getDescription() {
        return description;
    }

}