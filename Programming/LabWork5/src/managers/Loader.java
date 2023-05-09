/**

 The Loader class is used to read data from console or file, write data in CSV format, create Album and MusicBand objects, and convert strings to corresponding data types.

 */

package managers;

import builders.AlbumBuilder;
import builders.CoordinatesBuilder;
import builders.MusicBandBuilder;
import common.*;
import result.Result;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loader {

    /** Date formatting object for LocalDate class */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /** BufferedReader object for reading from input stream */
    private BufferedReader reader;

    private boolean isConsole;

    /**
     * Constructs an instance of Loader class with specified input stream
     * @param reader - input stream
     */
    public Loader(BufferedReader reader, boolean isConsole) {
        this.reader = reader;
        this.isConsole = isConsole;
    }

    /**
     * Checks whether the input stream is console
     * @return true, if the input stream is console, false - otherwise
     */
    public boolean isConsole() {
        try {
            return isConsole;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets a new input stream
     * @param reader - input stream
     */

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Reads album data from input stream
     * @return Result object containing the created Album object or error message
     */
    public Result<Album> enterAlbum() {
        try {
            if (!this.isConsole()) {
                return Result.success(new AlbumBuilder()
                        .setName(reader.readLine())
                        .setTracks(Long.parseLong(reader.readLine().trim()))
                        .setLength(Long.parseLong(reader.readLine().trim()))
                        .setSales(Float.parseFloat(reader.readLine().trim()))
                        .createAlbum());

            } else {
                AlbumBuilder builder = new AlbumBuilder();
                System.out.println("Enter album name:");
                builder.setName(reader.readLine());
                System.out.println("Enter number of tracks:");
                builder.setTracks(Long.parseLong(reader.readLine().trim()));
                System.out.println("Enter album length:");
                builder.setLength(Long.parseLong(reader.readLine().trim()));
                System.out.println("Enter album sales:");
                builder.setSales(Float.parseFloat(reader.readLine().trim()));
                return Result.success(builder.createAlbum());
            }
        }catch (Exception e){
            return Result.failure(e, "Error while reading album data.");
        }
    }

    /**
     * Parses an array of strings containing music band data and creates a MusicBand object
     * @param parts - an array of strings containing music band data
     * @return Result object containing the created MusicBand object or error message
     */
    public Result <MusicBand> parseMusicBand(String[] parts) {
        try {
            return Result.success(new MusicBandBuilder()
                    .setId(Long.parseLong(parts[0]))
                    .setName(parts[1])
                    .setCoordinates(new CoordinatesBuilder().setX(Long.parseLong(parts[2])).setY(Float.parseFloat(parts[3])).createCoordinates())
                    .setCreationDate(parseDate(parts[4]).getValue().orElseGet(() -> {
                        throw  new IllegalArgumentException("Creation date not specified.");
                    }))
                    .setNumberOfParticipants(Long.parseLong(parts[5]))
                    .setGenre(MusicGenre.valueOf(parts[6]))
                    .setBestAlbum(parts.length > 7 ? new Album(parts[7], Integer.parseInt(parts[8]), Long.parseLong(parts[9]), Float.parseFloat(parts[10])) : null)
                    .createMusicBand());
        }catch (Exception e){
            return Result.failure(e, "Error while parsing music band");
        }

    }
    /**
     * Change object MusicBand to CSV format
     * @param band - object MusicBand
     * @return object Result with CSV format or error message
     */
    public Result<String> toCSV(MusicBand band) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(band.getId()).append(",").append(band.getName()).append(",");
            sb.append(band.getCoordinates().getX()).append(",").append(band.getCoordinates().getY()).append(",");
            sb.append(band.getCreationDate().format(formatter)).append(",").append(band.getNumberOfParticipants()).append(",");
            sb.append(band.getGenre()).append(",");
            Album album = band.getBestAlbum();
            if (band.getBestAlbum() != null) {
                sb.append(album.getName()).append(",");
                sb.append(album.getTracks()).append(",");
                sb.append(album.getLength()).append(",");
                sb.append(album.getSales());
            } else {
                sb.append(",");
            }
            return Result.success(sb.toString());
        }catch (Exception e){
            return Result.failure(e, "Error with parsing CSV format");
        }
    }

    /**
     * Change String to LocalDate
     * @param formattedDate - String with date
     * @return object Result with LocalDate or error message
     */

    public Result<LocalDate> parseDate(String formattedDate) {
        try {
            return Result.success(LocalDate.parse(formattedDate, formatter));
        }catch (Exception e){
            return Result.failure(e,"Error with date format");
        }
    }

    /**
     * Reads id from input stream
     * @return object Result, containing read line or error message
     */
    public Result<Long> enterId() {
        if (this.isConsole())
            System.out.println("Enter ID: ");
        try {
            return Result.success(Long.parseLong(reader.readLine().trim()));
        }catch (Exception e){
            return Result.failure(e, "Error with getting ID");
        }
    }

    /**
     * Read band from input stream
     * @return object Result, containing read line or error message
     */
    public Result<MusicBand> enterBand() {
        try {
            Result<String> result;
            MusicBandBuilder builder = new MusicBandBuilder();
            if (this.isConsole())
                System.out.println("Enter band name:");
            result = this.readLine();
            if (result.isSuccess())
                builder.setName(result.getValue().get());
            else {
                return Result.failure(result.getError().get(), "Error while entering band name");
            }
            if (this.isConsole()) {
                System.out.println("Enter band coordinates:");
                System.out.print("x = ");
            }
            result = this.readLine();
            CoordinatesBuilder coordinates;
            if (result.isSuccess())
                coordinates = new CoordinatesBuilder().setX(Long.parseLong(result.getValue().get()));
            else {
                return Result.failure(result.getError().get(), "Error while entering band coordinates");
            }
            System.out.print("y = ");
            result = this.readLine();
            if (result.isSuccess())
                builder.setCoordinates(coordinates.setY(Float.parseFloat(result.getValue().get())).createCoordinates());
            else {
                return Result.failure(result.getError().get(), "Error while entering band coordinates");
            }

            if (this.isConsole())
                System.out.println("Enter number of participants:");
            builder.setNumberOfParticipants(Long.parseLong(reader.readLine().trim()));
            if (this.isConsole())
                System.out.println("Enter date of creation with format dd-mm-yyyy:");
            result = this.readLine();
            builder.setCreationDate(parseDate(result.getValue().get()).getValue().get());
            MusicGenre genre = null;
            if (this.isConsole()) {
                System.out.println("Enter genre:");
                while (genre == null) {
                    try {
                        System.out.print("Available genres:");
                        for (MusicGenre musicGenre : MusicGenre.values()) {
                            System.out.print(musicGenre.name() + " ");
                        }
                        System.out.println();
                        result = this.readLine();
                        genre = MusicGenre.valueOf(result.getValue().get().trim().toUpperCase());
                    } catch (Exception e) {
                        System.out.println("Error while entering genre");
                    }
                }
            } else
                genre = MusicGenre.valueOf(result.getValue().get().trim().toUpperCase());
            builder.setGenre(genre);
            builder.setBestAlbum(this.enterAlbum().getValue().get());
            return Result.success(builder.createMusicBand());
        }catch (Exception e){
            return Result.failure(e, "Error while entering band");
        }
    }

    /**
     * Loads collection from file
     * @param collection collection to load
     * @param filename filename to load from
     * @return object Result, containing null or error message
     */

    public Result<Void> load(Collection<MusicBand> collection, String filename) {
        String line;
        try {
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader reader = new BufferedReader(streamReader);
            line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                Result<MusicBand> musicBandResult = parseMusicBand(parts);
                if (musicBandResult.isSuccess()){
                    MusicBand musicBand = musicBandResult.getValue().get();
                    collection.add(musicBand);
                    MusicBand.setIdCounter(musicBand.getId());
                }else{
                    return Result.failure(musicBandResult.getError().get(),musicBandResult.getMessage());
                }
                line = reader.readLine();
            }
        }catch (Exception e){
            return Result.failure(e, "Error with loading file");
        }
        return Result.success(null);
    }

    /**
     * Reads line from input stream
     * @return object Result, containing line or error message
     */
    public Result<String> readLine() {
        try {
            String line = reader.readLine();
            if (line == null) {
                return Result.success("");
            } else {
                return Result.success(line);
            }
        } catch (Exception e) {
            return Result.failure(e, "Error with reading");
        }
    }

}
