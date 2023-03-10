package managers;

import Result.Result;
import music.Album;
import music.Collection;
import music.MusicBand;
import сommands.Command;

import java.io.*;
import java.util.Comparator;
import java.util.Deque;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Receiver {
    private final Collection<MusicBand> collection;

    private final Loader loader;

    public Receiver(Collection<MusicBand> collection, Loader loader) {
        this.collection = collection;
        this.loader = loader;
    }

    public Collection<MusicBand> getCollection() {
        return collection;
    }

    public Result<Void> add() {
        MusicBand band = null;
        try {
            Result<MusicBand> bandResult = loader.enterBand();
            if (bandResult.isSuccess()) {
                collection.add(band);
                System.out.println("New element successfully added to collection");
                return Result.success(null);
            } else {
                return Result.failure(bandResult.getError().orElse(null), bandResult.getMessage());
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with executing add command");
        }

    }

    public Result<Void> addIfMax() {
        MusicBand newBand, maxBand;
        Result<MusicBand> bandResult = loader.enterBand();
        if (bandResult.isSuccess()) {
            newBand = bandResult.getValue().get();
            Result<MusicBand> maxBandResult = collection.getMax();
            if (maxBandResult.isSuccess())
                maxBand = maxBandResult.getValue().get();
            else
                return Result.failure(maxBandResult.getError().get(), maxBandResult.getMessage());
            if (maxBand == null || newBand.compareTo(maxBand) > 0) {
                collection.add(newBand);
                if (loader.isConsole())
                    System.out.println("New band successfully added to collection.");
                return Result.success(null);
            } else {
                if (loader.isConsole())
                    System.out.println("New band is not the greatest element of collection, element is not added to collection");
                return Result.success(null);
            }
        } else {
            return Result.failure(bandResult.getError().get(), bandResult.getMessage());
        }

    }

    public Result<Void> clear() {
        Result<Void> result = collection.clear();
        if (result.isSuccess()) {
            if (loader.isConsole())
                System.out.println("Collection successfully cleared");
            return Result.success(null);
        } else
            return Result.failure(result.getError().get(), result.getMessage());
    }

    public Result<Void> countByBestAlbum() {
        try {
            Result<Album> resultBestAlbum = null;
            resultBestAlbum = loader.enterAlbum();
            if (!resultBestAlbum.isSuccess())
                return Result.failure(resultBestAlbum.getError().get(), resultBestAlbum.getMessage());
            Album bestAlbum = resultBestAlbum.getValue().get();
            int count = 0;
            for (var band : collection.getCollection()) {
                if (band.getBestAlbum().compareTo(bestAlbum) == 0)
                    count++;
            }
            if (loader.isConsole())
                System.out.println("Number of elements with best album equal to " + bestAlbum + " is " + count);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with executing countByBestAlbum command");
        }
    }

    public Result<Void> executeScriptCommand(Invoker commandManager) {
        String filename;
        if (loader.isConsole()) {
            System.out.println("Enter the name of the file containing the commands:");
        }

        Result<String> filenameResult = loader.readLine();
        if (filenameResult.isSuccess()) {
            filename = filenameResult.getValue().get();
        } else {
            return Result.failure(filenameResult.getError().get(), filenameResult.getMessage());
        }

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 1) {
                    return Result.failure(new Exception("Invalid command in file."), "Invalid command in file.");
                }
                if (!commandManager.addExecutedScript(filename)) {
                    return Result.failure(new Exception("Recursion detected."), "Recursion detected.");
                }
                commandManager.executeCommand(parts[0], this);
                commandManager.removeExecutedScript();
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Problem with executing program from file");
        }
    }

    public Result<Void> updateById() {
        MusicBand newBand = null;
        long id;
        try {
            Result<Long> idResult = loader.enterId();
            if (idResult.isSuccess()) {
                id = idResult.getValue().get();
            } else {
                return Result.failure(idResult.getError().get(), idResult.getMessage());
            }
            newBand = loader.enterBand().getValue().get();
        } catch (Exception e) {
            return Result.failure(e, "Error with entering ID");
        }

        Result<MusicBand> oldBand = this.getById(id);
        if (oldBand.isSuccess()) {
            Result<Void> updateResult = this.update(id, newBand);
        }

        return Result.success(null);
    }

    private Result<Void> update(long id, MusicBand newBand) {
        collection.remove(findById(id));
        newBand.setId(id);
        return Result.success(null);
    }

    public MusicBand findById(long id) {
        return collection.getCollection()
                .stream()
                .filter((MusicBand element) -> element.getId() == id)
                .findFirst().
                orElse(null);
    }


    public Result<MusicBand> getById(long id) {
        try {
            return Result.success(findById(id));
        } catch (Exception e) {
            return Result.failure(e, "Problem with getting by ID");
        }
    }

    public Result<Void> removeById() {
        try {
            long id;
            try {
                Result<Long> idResult = loader.enterId();
                if (idResult.isSuccess()) {
                    id = idResult.getValue().get();
                } else {
                    return Result.failure(idResult.getError().get(), idResult.getMessage());
                }
            } catch (Exception e) {
                return Result.failure(e, "Error with entering ID");
            }
            try {
                collection.setCollection(collection
                        .getCollection()
                        .stream()
                        .filter((MusicBand band) -> band.getId() == id)
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

    public Result<Void> removeGreaterCommand() {
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

    public Result<Void> saveCollection() {
        String filename;
        if (loader.isConsole()) {
            System.out.println("Enter the name of the file to save the collection:");
        }
        Result<String> filenameResult = loader.readLine();
        if (filenameResult.isSuccess()) {
            filename = filenameResult.getValue().get();
        } else {
            return Result.failure(filenameResult.getError().get(), filenameResult.getMessage());
        }
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

    public Result<Void> printHelpInfo(Map<String, Command> commands) {
        try {
            if (loader.isConsole())
                System.out.println("List of commands:");
            for (String commandName : commands.keySet()) {
                System.out.println(commandName);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing help info");
        }
    }

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

    public Result<Void> showElementsOfCollection() {
        try {
            if (loader.isConsole())
                System.out.println("Collection elements:");
            for (MusicBand element: collection.getCollection()) {
                System.out.println(element);
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with printing collection elements");
        }
    }
}
