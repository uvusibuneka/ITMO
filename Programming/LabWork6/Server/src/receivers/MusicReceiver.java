package receivers;

import builders.CoordinatesBuilder;
import builders.MusicBandBuilder;
import commands.Command;
import common.Album;
import common.MusicBand;
import common.MusicGenre;
import managers.Invoker;
import result.Result;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, хранящий коллекцию и выполняющий все операции с ней. Принимает запросы на выполнение команды. Реализует паттерн одиночка.
 *
 * @author Фролов К.Д.
 */
public class MusicReceiver extends Receiver<MusicBand>{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /**
     * коллекция, хранящая города
     */
    private final common.Collection<MusicBand> collection;
    /**
     * путь к csv файлу, из которого загружены данные в коллекцию в начале работы и будут сохраняться при команде {@link MusicReceiver#saveCollection()} ()}
     */
    private final String fileName;
    /**
     * Ссылка на текущий {@link Receiver}. Класс реализует паттерн одиночка.
     */
    private static MusicReceiver instance;

    /**
     * Здесь происходит вся инициализация: инициализация коллекции, подключение к файлу, его чтение и запись данных в коллекцию. Происходит одна один раз - спасибо паттерну одиночка.
     */
    private MusicReceiver() {
        fileName = System.getenv("FILE_NAME");
        collection = new common.Collection<>();

        String fileName = System.getenv("FILE_NAME");
        if (fileName == null) {
            System.out.println("Environment variable FILE_NAME is not set");
            System.exit(0);
        }

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File " + fileName + " does not exist");
            return;
        }

        if (!file.canRead()) {
            System.out.println("File " + fileName + " is not readable");
            return;
        }

        Result<Void> resultLoad = load(fileName);
        if (!resultLoad.isSuccess()) {
            System.out.println("Collection is not loaded: " + resultLoad.getMessage());
            System.exit(0);
        }
        System.out.println("Итого элементов в коллекции - " + collection.getSize());
    }

    /**
     * Loads collection from file
     *
     * @param filename filename to load from
     * @return object Result, containing null or error message
     */
    private Result<Void> load(String filename) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                Result<MusicBand> musicBandResult = parseMusicBand(parts);
                if (musicBandResult.isSuccess()) {
                    MusicBand musicBand = musicBandResult.getValue().get();
                    if (!collection.isUnique(musicBand.getID())) {
                        collection.clear();
                        return Result.failure(new IllegalArgumentException("ID must be unique"), "Error with loading file, ID must be unique for every element of collection");
                    }
                    collection.add(musicBand);
                } else {
                    return Result.failure(musicBandResult.getError().get(), musicBandResult.getMessage());
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with loading file");
        }
        return Result.success(null);
    }

    /**
     * Parses an array of strings containing music band data and creates a MusicBand object
     *
     * @param parts - an array of strings containing music band data
     * @return Result object containing the created MusicBand object or error message
     */
    private Result<MusicBand> parseMusicBand(String[] parts) {
        try {
            return Result.success(new MusicBandBuilder()
                    .setId(Long.parseLong(parts[0]))
                    .setName(parts[1])
                    .setCoordinates(new CoordinatesBuilder().setX(Long.parseLong(parts[2])).setY(Float.parseFloat(parts[3])).build())
                    .setCreationDate(parseDate(parts[4]).getValue().orElseThrow(() -> new IllegalArgumentException("Creation date not specified.")))
                    .setNumberOfParticipants(Long.parseLong(parts[5]))
                    .setGenre(MusicGenre.valueOf(parts[6]))
                    .setBestAlbum(parts.length > 7 ? new Album(parts[7], Integer.parseInt(parts[8]), Long.parseLong(parts[9]), Float.parseFloat(parts[10])) : null)
                    .build());
        } catch (Exception e) {
            return Result.failure(e, "Error while parsing music band");
        }

    }

    /**
     * Change String to LocalDate
     *
     * @param formattedDate - String with date
     * @return object Result with LocalDate or error message
     */
    public Result<LocalDate> parseDate(String formattedDate) {
        try {
            return Result.success(LocalDate.parse(formattedDate, formatter));
        } catch (Exception e) {
            return Result.failure(e, "Error with date format");
        }
    }

    /**
     * Одиночка. Если объекта нет - создадим, иначе вернем тот, что есть.
     *
     * @return ссылка на существующий объект {@link Receiver}
     */
    public static MusicReceiver GetInstance() {
        if (instance == null) {
            instance = new MusicReceiver();
        }
        return instance;
    }


    /**
     * Возвращает информацию о коллекции (тип, дата инициализации, количество элементов)
     *
     * @return объект {@link Result} с ответом
     */
    public Result<String> info() {
        return Result.success("Тип коллекции: " + collection.getClass().getName() +
                "\nКоличество элементов: " + collection.getSize() +
                "\nДата инициализации: " + collection.getInitializationDate());
    }

    /**
     * Возвращает все элементы коллекции в массиве
     *
     * @return объект {@link Result} с ответом
     */
    public Result<MusicBand[]> showElementsOfCollection() {
        MusicBand[] arr = new MusicBand[0];
        arr = collection.getCollection().toArray(arr);
        return Result.success(arr);
    }

    /**
     * Добавляет новый элемент с заданным ключом
     *
     * @param obj объект, который нужно положить в коллекцию
     * @return объект {@link Result} с информацией об ошибке или добавленным объектом при успехе
     */
    public Result<Void> add(MusicBand obj) {
        try {
            Result<Void> addResult = collection.add(obj);
            if (addResult.isSuccess()) {
                System.out.println("New element successfully added to collection");
                return Result.success(null, "New element successfully added to collection");
            } else {
                return Result.failure(addResult.getError().orElse(null), addResult.getMessage());
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with executing add command");
        }

    }

    /**
     * Method to add an element to the collection if it is greater than all elements of the collection
     *
     * @return the execution result of the command (success/failure)
     */
    public Result<Void> addIfMax(MusicBand obj) {
        MusicBand newBand, maxBand;
        newBand = obj;
        Result<MusicBand> maxBandResult = collection.getMax();
        if (maxBandResult.isSuccess()) {
            maxBand = maxBandResult.getValue().get();
        } else {
            return Result.failure(maxBandResult.getError().get(), maxBandResult.getMessage());
        }
        if (maxBand == null || newBand.compareTo(maxBand) > 0) {
            Result<Void> addResult = collection.add(newBand);
            if (addResult.isSuccess()) {
                return Result.success(null, "New band successfully added to collection.");
            } else {
                return Result.failure(addResult.getError().get(), addResult.getMessage());
            }
        } else {
            return Result.success(null, "New band is not the greatest element of collection, element is not added to collection");
        }
    }


    /**
     * Обновляет значение элемента коллекции, {@link MusicBand} которого равен заданному
     *
     * @param id      {@link MusicBand}, по которому будет обновлен объект
     * @param newBand новый объект, который нужно положить в коллекцию
     * @return объект {@link Result} с информацией об ошибке или сообщением об успехе
     */
    public Result<Void> updateById(long id, MusicBand newBand) {
        try {
            if (findById(id) == null) {
                return Result.failure(new Exception("Element with such ID is not found"), "Element with such ID is not found");
            }

            Result<Void> result = collection.remove(findById(id));
            if (result.isSuccess()) {
                newBand.setID(id);
                result = collection.add(newBand);
                if (result.isSuccess()) {
                    return Result.success(null, "Element successfully updated");
                } else
                    return Result.failure(result.getError().get(), result.getMessage());
            } else
                return Result.failure(result.getError().get(), result.getMessage());
        } catch (Exception e) {
            return Result.failure(e, "Error with executing updateById command");
        }
    }

    /**
     * Method for finding element in collection by id
     *
     * @param id id of element to find
     * @return element with such id or null if element is not found
     */
    public MusicBand findById(long id) {
        return collection.getCollection()
                .stream()
                .filter((MusicBand element) -> element.getID() == id)
                .findFirst().
                orElse(null);
    }

    /**
     * Method for removing element from collection by id
     *
     * @param id
     * @return result of executing command (success/error)
     */
    public Result<Void> removeById(long id) {
        try {
            collection.setCollection(collection
                    .getCollection()
                    .stream()
                    .filter((MusicBand band) -> band.getID() != id)
                    .collect(Collectors.toCollection(TreeSet::new))
            );
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e, "Error with removing element");
        }
    }

    /**
     * очистить коллекцию
     *
     * @return объект {@link Result} с сообщением об успехе
     */
    public Result<Void> clear() {
        Result<Void> result = collection.clear();
        if (result.isSuccess()) {
            return Result.success(null, "Collection successfully cleared");
        } else {
            return Result.failure(result.getError().orElse(null), result.getMessage());
        }
    }

    /**
     * сохранить коллекцию в файл
     *
     * @return объект {@link Result} с информацией об ошибке или сообщением об успехе
     * @see MusicReceiver#fileName
     */
    public Result<Void> saveCollection() {
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            for (MusicBand musicBand : collection.getCollection()) {
                Result<String> result = musicBand.toCSV();
                String csvStr;
                if (result.isSuccess())
                    csvStr = result.getValue().get();
                else
                    return Result.failure(result.getError().get(), result.getMessage());
                outputStream.write((csvStr + "\n").getBytes());
            }
            outputStream.close();
            return Result.success(null, "Collection saved to " + fileName);
        } catch (Exception e) {
            return Result.failure(e, "Error with saving collection to " + fileName);
        }
    }

    /**
     * удаляет из коллекции все элементы, превышающие заданный
     *
     * @param element объект {@link MusicBand}, с которым будут сравнивать объекты коллекции
     * @return {@link Result} of executing command (success/error)
     */
    public Result<Void> removeGreater(MusicBand element) {
        Result<Void> removeGreaterResult = collection.removeGreater(element);
        if (removeGreaterResult.isSuccess()) {
            return Result.success(null);
        } else {
            return Result.failure(new Exception("Greater elements are not found"));
        }
    }

    /**
     * Method for filtering collection by best album
     *
     * @param album best album
     * @return result of executing command (success/error)
     */
    public Result<TreeSet<MusicBand>> filterByBestAlbum(Album album) {
        try {
            return Result.success(
                    collection.getCollection()
                            .stream()
                            .filter(
                                    (MusicBand band) -> band.getBestAlbum().equals(album))
                            .collect(Collectors.toCollection(TreeSet::new)));
        } catch (Exception e) {
            return Result.failure(e, "Error with filtering by best album");
        }
    }

    /**
     * Method for printing info about max element of collection by best album
     *
     * @return result of executing command (success/error)
     */
    public Result<MusicBand> maxByBestAlbum() {
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
            return Result.success(maxAlbumBand);
        } catch (Exception e) {
            return Result.failure(e, "Error with finding the maximum by best album");
        }
    }

    /**
     * Method to count the elements in the collection whose bestAlbum field is equal to the specified value
     *
     * @param bestAlbum
     * @return the execution result of the command (success/failure) with the number of elements
     */
    public Result<Long> countByBestAlbum(Album bestAlbum) {
        try {
            long count = collection.getCollection().stream()
                    .filter((MusicBand band) -> band.getBestAlbum().compareTo(bestAlbum) == 0).count();
            return Result.success(count, "Number of elements with best album equal to " + bestAlbum + " is " + count);
        } catch (Exception e) {
            return Result.failure(e, "Error with executing countByBestAlbum command");
        }
    }
/*
    @Override
    public Result<Void> executeScript(Invoker invoker, String[] args) {
        return null;
    }

    @Override
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

    @Override
    public Result<Void> printHelpInfo(Map<String, Command> commands) {
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

    @Override
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

*/
}
