/**

 The Collection class represents a collection of objects parameterized by type T, which must be comparable.
 The collection is implemented as a TreeSet.
 */
package common;
import result.Result;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.TreeSet;

public class Collection<T extends Comparable & IDAccess> {

    private TreeSet<T> collection = new TreeSet<T>();

    private static HashSet<Long> ids = new HashSet<Long>();

    private LocalDate initializationDate = LocalDate.now();

    /**
     * Adds an element to the collection.
     *
     * @param element the element to add.
     * @return the result of the operation as a Result object. Returns null in case of success.
     * Returns a Result object with an exception and an error message in case of failure.
     */
    public Result<Void> add(T element) {
        try {
            if(!ids.contains(element.getID()))
                ids.add(element.getID());
            else
                throw new IllegalArgumentException("The id of the element is already in use.");
            collection.add(element);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error while adding element to collection");
        }
    }

    /**
     * Clears the collection.
     *
     * @return the result of the operation as a Result object. Returns null in case of success.
     * Returns a Result object with an exception and an error message in case of failure.
     */
    public Result<Void> clear() {
        try {
            collection.clear();
            ids.clear();
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error while clearing collection");
        }
    }

    /**
     * Returns the collection.
     *
     * @return the collection.
     */
    public TreeSet<T> getCollection() {
        return collection;
    }

    /**
     * Sets the collection.
     *
     * @param collection the collection.
     */
    public void setCollection(TreeSet<T> collection) {
        this.collection = collection;
    }

    /**
     * Returns the maximum element of the collection.
     *
     * @return the result of the operation as a Result object. Returns the maximum element of the collection in case of success.
     * Returns a Result object with an exception and an error message in case of failure.
     */
    public Result<T> getMax() {
        if (this.getSize() == 0) {
            return Result.failure(new Exception("Collection is empty"), "Collection is empty");
        }

        T max = collection.first();
        for (T element : collection) {
            if (max.compareTo(element) < 0) {
                max = element;
            }
        }

        return Result.success(max);
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection.
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Returns the date when the collection was initialized.
     *
     * @return the date when the collection was initialized.
     */
    public String getInitializationDate() {
        return initializationDate.toString();
    }

    /**
     * Removes from the collection all elements greater than the specified element.
     *
     * @param element the element with respect to which the removal takes place.
     * @return the result of the operation as a Result object. Returns an empty Result in case of success.
     * Returns a Result object with an exception and an error message in case of failure.
     */
    public Result<Void> removeGreater(T element) {
        try {
            boolean flag = false;
            for (T element1 : collection) {
                if (element1.compareTo(element) > 0) {
                    collection.remove(element1);
                    flag = true;
                }
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Failed to remove greater elements");
        }
    }

    /**
     * Removes the specified element from the collection.
     *
     * @param element element to remove.
     * @return result of the operation as a Result object. В случае успеха - пустой Result,
     * in case of failure - Result object with an exception and an error message.
     */
    public Result<Void> remove(T element) {
        try {
            collection.remove(element);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Failed to remove element");
        }
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise.
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Checks if the collection contains the specified element with ID.
     *
     * @param id the element to check.
     * @return true if the collection contains the specified element, false otherwise.
     */
    public boolean isUnique(long id) {
        return !ids.contains(id);
    }
}