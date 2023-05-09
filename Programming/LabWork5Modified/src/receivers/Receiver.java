/**

 The Receiver abstract class provides a template for objects that will receive and handle
 the execution of commands in response to user input.
 It defines a collection attribute and abstract methods for handling specific commands.
 */
package receivers;

import commands.Command;
import common.Collection;
import managers.Invoker;
import result.Result;

import java.util.Deque;
import java.util.Map;

public abstract class Receiver {
    private Collection collection;

    /**
     * Abstract method for adding an element to the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> add();

    /**
     * Abstract method for clearing the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> clear();

    /**
     * Abstract method for adding an element to the collection if its value is greater than the maximum element in the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> addIfMax();

    /**
     * Abstract method for counting the number of elements in the collection with the same value for the 'bestAlbum' field.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> countByBestAlbum();

    /**
     * Abstract method for executing a script of commands.
     *
     * @param invoker the Invoker object that will execute the commands.
     * @param args number of arguments for the command.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> executeScript(Invoker invoker, String[] args);

    /**
     * Abstract method for exiting the program.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> exit();

    /**
     * Abstract method for filtering the collection by the 'bestAlbum' field.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> filterByBestAlbum();

    /**
     * Abstract method for printing help information about the available commands.
     * @param commands a Map object that maps command names to Command objects.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> printHelpInfo(Map<String, Command> commands);

    /**
     * Abstract method for printing the command history.
     * @param history a Deque object that stores the history of executed commands.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> printHistory(Deque<String> history);

    /**
     * Abstract method for printing information about the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> info();

    /**
     * Abstract method for finding the element in the collection with the maximum value for the 'bestAlbum' field.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> maxByBestAlbum();

    /**
     * Abstract method for removing an element from the collection by its ID.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> removeById(String[] args);

    /**
     * Abstract method for removing all elements from the collection that are greater than the specified element.
     * @return a Result object that indicates the success or failure of the operation.
     */

    public abstract Result<Void> removeGreater();

    /**
     * Abstract method for saving the collection to a file.
     * @return a Result object that indicates the status of the save operation.
     */
    public abstract Result<Void> saveCollection();

    /**

     Abstract method for displaying all elements of the collection.
     @return a Result object that indicates the status of the display operation.
     */
    public abstract Result<Void> showElementsOfCollection();
    /**

     Abstract method for updating an element of the collection by ID.
     @return a Result object that indicates the status of the update operation.
     */
    public abstract Result<Void> updateById(String[] args);
}
