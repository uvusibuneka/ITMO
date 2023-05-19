/**

 The Receiver abstract class provides a template for objects that will receive and handle
 the execution of commands in response to user input.
 It defines a collection attribute and abstract methods for handling specific commands.
 */
package receivers;

import commands.Command;
import common.Album;
import common.Collection;
import common.IDAccess;
import common.MusicBand;
import managers.Invoker;
import result.Result;

import java.util.Deque;
import java.util.Map;
import java.util.TreeSet;

public abstract class Receiver<T extends Comparable<T> & IDAccess> {
    private Collection<T> collection;

    /**
     * Abstract method for adding an element to the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> add(T obj);

    /**
     * Abstract method for clearing the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> clear();

    /**
     * Abstract method for adding an element to the collection if its value is greater than the maximum element in the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> addIfMax(T obj);

    /**
     * Abstract method for printing information about the collection.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<String> info();

    /**
     * Abstract method for removing an element from the collection by its ID.
     * @return a Result object that indicates the success or failure of the operation.
     */
    public abstract Result<Void> removeById(long id);

    /**
     * Abstract method for removing all elements from the collection that are greater than the specified element.
     * @return a Result object that indicates the success or failure of the operation.
     */

    public abstract Result<Void> removeGreater(T element);

    /**
     * Abstract method for saving the collection to a file.
     * @return a Result object that indicates the status of the save operation.
     */
    public abstract Result<Void> saveCollection();

    /**

     Abstract method for displaying all elements of the collection.
     @return a Result object that indicates the status of the display operation.
     */
    public abstract Result<T[]> showElementsOfCollection();
    /**

     Abstract method for updating an element of the collection by ID.
     @return a Result object that indicates the status of the update operation.
     */
    public abstract Result<Void> updateById(long id, T newElement);
}
