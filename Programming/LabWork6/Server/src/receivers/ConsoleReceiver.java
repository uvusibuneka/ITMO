/**
 * The ConsoleReceiver class that contains command implementation
 */
package receivers;

import commands.Command;
import common.Album;
import common.Collection;
import common.MusicBand;
import managers.Invoker;
import managers.Loader;
import result.Result;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleReceiver extends Receiver {

    /**
     * The collection of MusicBand objects
     */
    private final Collection<MusicBand> collection;

    /**
     * The object responsible for data input
     */
    private Loader loader;

    private String filename;

    /**
     * ConsoleReceiver class constructor
     * @param collection the collection of MusicBand objects
     * @param loader the object responsible for data input
     */

    public ConsoleReceiver(Collection<MusicBand> collection, Loader loader) {
        this.collection = collection;
        this.loader = loader;
    }

    /**
     * Method to get the collection
     * @return the collection
     */
    public Collection<MusicBand> getCollection() {
        return collection;
    }

    /**
     * Method to add an element to the collection
     * @return the execution result of the command (success/failure)
     */
    public Result<Void> add() {
        MusicBand band = null;
        try {
            Result<MusicBand> bandResult = loader.enterBand();
            if (bandResult.isSuccess()) {
                Result<Void> addResult = collection.add(bandResult.getValue().get());
                if (addResult.isSuccess()) {
                    System.out.println("New element successfully added to collection");
                    return Result.success(null);
                } else {
                    return Result.failure(addResult.getError().orElse(null), addResult.getMessage());
                }
            } else {
                return Result.failure(bandResult.getError().orElse(null), bandResult.getMessage());
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with executing add command");
        }

    }

    /**
     * Method to add an element to the collection if it is greater than all elements of the collection
     * @return the execution result of the command (success/failure)
     */

    public Result<Void> addIfMax() {
        MusicBand newBand, maxBand;
        Result<MusicBand> bandResult = loader.enterBand();
        if (bandResult.isSuccess()) {
            newBand = bandResult.getValue().get();
            Result<MusicBand> maxBandResult = collection.getMax();
            if (maxBandResult.isSuccess()) {
                maxBand = maxBandResult.getValue().get();
            } else
                return Result.failure(maxBandResult.getError().get(), maxBandResult.getMessage());
            if (maxBand == null || newBand.compareTo(maxBand) > 0) {
                Result<Void> addResult = collection.add(newBand);
                if (addResult.isSuccess()) {
                    if (loader.isConsole())
                        System.out.println("New band successfully added to collection.");
                    return Result.success(null);
                } else {
                    return Result.failure(addResult.getError().get(), addResult.getMessage());
                }
            } else {
                if (loader.isConsole())
                    System.out.println("New band is not the greatest element of collection, element is not added to collection");
                return Result.success(null);
            }
        } else {
            return Result.failure(bandResult.getError().get(), bandResult.getMessage());
        }
    }

    /**

     Method to clear the collection
     @return the execution result of the command (success/failure)
     */
    public Result<Void> clear() {
        Result<Void> result = collection.clear();
        if (result.isSuccess()) {
            if (loader.isConsole())
                System.out.println("Collection successfully cleared");
            return Result.success(null);
        } else {
            return Result.failure(result.getError().orElse(null), result.getMessage());
        }
    }

    /**

     Method to count the elements in the collection whose bestAlbum field is equal to the specified value
     @return the execution result of the command (success/failure) with the number of elements
     */
    public Result<Void> countByBestAlbum() {
        try {
            Result<Album> resultBestAlbum = loader.enterAlbum();
            if (!resultBestAlbum.isSuccess()) {
                return Result.failure(resultBestAlbum.getError().orElse(null), resultBestAlbum.getMessage());
            }
            Album bestAlbum = resultBestAlbum.getValue().get();
            int count = 0;
            for (var band : collection.getCollection()) {
                if (band.getBestAlbum().compareTo(bestAlbum) == 0) {
                    count++;
                }
            }
            if (loader.isConsole()) {
                System.out.println("Number of elements with best album equal to " + bestAlbum + " is " + count);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with executing countByBestAlbum command");
        }
    }

    /**
     * Method for executing script from file
     *
     * @param commandManager manager for executing commands
     * @param args number of arguments
     * @return result of executing command (success/error)
     */
    public Result<Void> executeScript(Invoker commandManager, String[] args) {
        String filename = args[1];
        String line;
        Loader previousLoader = commandManager.getLoader();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        }catch (FileNotFoundException e) {
            return Result.failure(e, "File not found");
        }

        Loader newLoader = new Loader(reader, false);
        commandManager.setLoader(newLoader);
        if(!commandManager.addExecutedScript(filename))
            return Result.failure(new Exception("Recursion"),"Script is already executing, recursion is not allowed");

        try {
            this.setLoader(newLoader);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                Result result = commandManager.executeCommand(parts[0], parts, this);
                if (!result.isSuccess()) {
                    commandManager.removeExecutedScript();
                    commandManager.setLoader(previousLoader);
                    this.setLoader(previousLoader);
                    return result;
                }
            }
            commandManager.removeExecutedScript();
            commandManager.setLoader(previousLoader);
            this.setLoader(previousLoader);
            return Result.success(null);
        } catch (Exception e) {
            commandManager.removeExecutedScript();
            commandManager.setLoader(previousLoader);
            this.setLoader(previousLoader);
            return Result.failure(e, "Problem with executing program from file");
        }
    }

    private void setLoader(Loader newLoader) {
        this.loader = newLoader;
    }

    /**
     * Method for realization of command Update
     * @return result of executing command (success/error)
     */

    public Result<Void> updateById(String[] args) {
        MusicBand newBand = null;
        int id;
        try {
            id = Integer.parseInt(args[1]);
            if (findById(id) == null) {
                return Result.failure(new Exception("Element with such ID is not found"), "Element with such ID is not found");
            }

            Result<MusicBand> bandResult = loader.enterBand();
            if (bandResult.isSuccess()) {
                newBand = bandResult.getValue().get();
            } else {
                return Result.failure(bandResult.getError().get(), bandResult.getMessage());
            }

            return update(id, newBand);
        } catch (Exception e) {
            return Result.failure(e, "Error with executing updateById command");
        }
    }

    /**
     * Method for updating element in collection
     * @param id id of element to update
     * @param newBand new element
     * @return result of executing command (success/error)
     */

    private Result<Void> update(int id, MusicBand newBand) {
        Result<Void> result = collection.remove(findById(id));
        if (result.isSuccess()) {
            newBand.setID(id);
            result = collection.add(newBand);
            if (result.isSuccess()) {
                if (loader.isConsole())
                    System.out.println("Element successfully updated");
                return Result.success(null);
            } else
                return Result.failure(result.getError().get(), result.getMessage());
        } else
            return Result.failure(result.getError().get(), result.getMessage());
    }

    /**
     * Method for finding element in collection by id
     * @param id id of element to find
     * @return element with such id or null if element is not found
     */
    public MusicBand findById(int id) {
        return collection.getCollection()
                .stream()
                .filter((MusicBand element) -> element.getID() == id)
                .findFirst().
                orElse(null);
    }

    /**
     * Method for getting element from collection by id
     * @param id id of element to get
     * @return object Result with element or error
     */

    public Result<MusicBand> getById(int id) {
        try {
            return Result.success(findById(id));
        } catch (Exception e) {
            return Result.failure(e, "Problem with getting by ID");
        }
    }

    /**
     * Method for removing element from collection by id
     * @return result of executing command (success/error)
     */

    public Result<Void> removeById(String[] args) {
        int id;
        try {
            try {
                id = Integer.parseInt(args[1]);
            } catch (Exception e) {
                return Result.failure(e, "Error with entering ID");
            }
            try{
                collection.setCollection(collection
                        .getCollection()
                        .stream()
                        .filter((MusicBand band) -> band.getID() == id)
                        .collect(Collectors.toCollection(TreeSet::new))
                );
                return Result.success(null);
            } catch (Exception e) {
                return Result.failure(e, "Error with removing element");
            }
        } catch (Exception e) {
            return Result.failure(e, "Error entering ID");
        }
    }

    /**
     * Method for removing elements from collection which are greater than entered
     * @return result of executing command (success/error)
     */

    public Result<Void> removeGreater() {
        try {
            MusicBand band;
            Result<MusicBand> bandResult = loader.enterBand();
            if (bandResult.isSuccess()) {
                band = bandResult.getValue().get();
            } else {
                return Result.failure(bandResult.getError().get(), bandResult.getMessage());
            }
            Result<Void> removeGreaterResult = collection.removeGreater(band);
            if (removeGreaterResult.isSuccess()) {
                return Result.success(null);
            } else {
                return Result.failure(new Exception("Greater elements are not found"));
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with entering band");
        }
    }


    /**
     * Method for saving collection to file
     * @return result of executing command (success/error)
     */
    public Result<Void> saveCollection() {
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename));
            for (MusicBand musicBand : collection.getCollection()) {
                Result<String> result = loader.toCSV(musicBand);
                String csvStr;
                if (result.isSuccess())
                    csvStr = result.getValue().get();
                else
                    return Result.failure(result.getError().get(), result.getMessage());
                outputStream.write((csvStr + "\n").getBytes());
            }
            outputStream.close();
            if (loader.isConsole())
                System.out.println("Collection saved to " + filename);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with saving collection to " + filename);
        }
    }

    /**
     * Method for printing history of commands
     * @param history history of commands
     * @return result of executing command (success/error)
     */

    public Result<Void> printHistory(Deque<String> history) {
        try {
            if (loader.isConsole())
                System.out.println("History of recent commands:");
            for (String command : history) {
                System.out.println(command);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing history");
        }
    }

    /**
     * Method for printing info about max element of collection by best album
     * @return result of executing command (success/error)
     */
    public Result<Void> maxByBestAlbum() {
        if (collection.getSize() == 0) {
            return Result.failure(new Exception("Max element of collection does not exist"), "Collection is empty");
        }
        try {
            MusicBand maxAlbumBand = collection.getCollection()
                    .stream()
                    .max(Comparator.comparing(band -> band.getBestAlbum().getSales()))
                    .orElse(null);
            if (maxAlbumBand == null) {
                return Result.failure(new Exception("Max element of collection does not exist"), "Max element of collection does not exist");
            }
            if (loader.isConsole())
                System.out.println("Max element of collection:");
            System.out.println(maxAlbumBand);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with finding the maximum by best album");
        }
    }

    /**
     * Method for ending the program
     * @return result of executing command (success/error)
     */
    public Result<Void> exit() {
        try {
            if (loader.isConsole()) {
                System.out.println("Exiting...");
                System.exit(0);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with exiting");
        }
    }

    /**
     * Method for filtering collection by best album
     * @return result of executing command (success/error)
     */

    public Result<Void> filterByBestAlbum() {
        try {
            Album album;
            Result<Album> albumResult = loader.enterAlbum();
            if (albumResult.isSuccess()) {
                album = albumResult.getValue().get();
            } else {
                return Result.failure(albumResult.getError().get(), albumResult.getMessage());
            }
            try {
                System.out.println("Filtered collection:");
                System.out.println(collection.getCollection()
                        .stream()
                        .filter((MusicBand band) -> band.getBestAlbum().equals(album))
                        .collect(Collectors.toCollection(TreeSet::new)).toString());
                return Result.success(null);
            } catch (Exception e) {
                return Result.failure(e, "Error with filtering by best album");
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with entering album");
        }
    }

    /**
     * Method for printing list of commands and their descriptions
     * @param commands list of commands
     * @return result of executing command (success/error)
     */

    public Result<Void> printHelpInfo(Map<String,Command> commands) {
        try {
            if (loader.isConsole())
                System.out.println("List of commands:");
            for (String commandName : commands.keySet()) {
                System.out.println(commands.get(commandName).getDescription());
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing help info");
        }
    }

    /**
     * Method for printing collection info
     * @return result of executing command (success/error)
     */
    public Result<Void> info() {
        try {
            if (loader.isConsole())
                System.out.println("Collection info:");
            System.out.println("Type: " + collection.getCollection().getClass().getName());
            System.out.println("Date of initialization: " + collection.getInitializationDate());
            System.out.println("Number of elements: " + collection.getSize());
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing collection info");
        }
    }

    /**
     * Method for printing elements of collection
     * @return result of executing command (success/error)
     */
    public Result<Void> showElementsOfCollection() {
        try {
            if (!collection.isEmpty()) {
                if (loader.isConsole())
                    System.out.println("Collection elements:");
                Class<?> clazz = collection.getCollection().iterator().next().getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        System.out.print(field.getName() + "\t");
                    }
                }
                System.out.println();
                for (Object element : collection.getCollection()) {
                    System.out.print(element.toString() + "\t");
                    for (Field field : fields) {
                        if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                            field.setAccessible(true);
                            System.out.print(field.get(element) + "\t");
                        }
                    }
                    System.out.println();
                }

            } else {
                if (loader.isConsole())
                    System.out.println("Collection is empty");
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing collection elements");
        }
    }

    /**
     * Method for setting filename
     * @param fileName filename
     */
    public void setFileName(String fileName) {
        this.filename = fileName;
    }
}
