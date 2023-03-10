package music;

import result.Result;

import java.time.LocalDate;
import java.util.TreeSet;

public class Collection<T extends Comparable> {
    private TreeSet<T> collection = new TreeSet<T>();
    private LocalDate initializationDate = LocalDate.now();

    public Result<Void> add(T element) {
        try {
            collection.add(element);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Failed to add element");
        }
    }

    public Result<Void> clear() {
        try {
            collection.clear();
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Failed to clear collection");
        }
    }

    public TreeSet<T> getCollection() {
        return collection;
    }

    public void setCollection(TreeSet<T> collection) {
        this.collection = collection;
    }


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

    public int getSize() {
        return collection.size();
    }

    public String getInitializationDate() {
        return initializationDate.toString();
    }

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

    public Result<Boolean> remove(T element) {
        try {
            collection.remove(element);
            return Result.success(true);
        } catch (Exception e) {
            return Result.failure(e, "Failed to remove element");
        }
    }
}