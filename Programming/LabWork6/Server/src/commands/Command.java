package commands;

import receivers.*;
import result.Result;
/**
 * Базовый интерфейс для всех возможных команд.
 */
public abstract class Command {
    Receiver receiver;
    /**
     * Constructor for creating a command object.
     */
    private final String description;

    /**
     *
     * @param description the description of the command
     */
    public Command(String description) {
        this.description = description;
    }

    /**
     * A method that will be called when executing the command.
     * @return the result of executing the command
     */
    public abstract Result<Void> execute();

    /**
     * A method that returns the description of the command.
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }
}