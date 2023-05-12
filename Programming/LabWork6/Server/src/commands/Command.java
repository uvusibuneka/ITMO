package commands;

import receivers.*;
import result.Result;

public abstract class Command {

    /**

     Constructor for creating a command object.
     @param description the description of the command
     */
    private String description;
    private int args;
    public Command(String description, int args) {
        this.description = description;
        this.args = args;
    }

    /**

     A method that will be called when executing the command.
     @param receiver an object that will execute the command
     @return the result of executing the command
     */
    public abstract Result<Void> execute(Receiver receiver, String args[]);

    /**

     A method that returns the description of the command.
     @return the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the number of arguments
     * @return number of arguments
     */

    public int getArgs() {
    	return args;
    }

}