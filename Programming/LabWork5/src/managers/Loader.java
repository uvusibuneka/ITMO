package managers;

import Result.Result;
import builders.AlbumBuilder;
import builders.CoordinatesBuilder;
import builders.MusicBandBuilder;
import music.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private BufferedReader reader;
    public Loader(BufferedReader reader) {
        this.reader = reader;
    }

    public boolean isConsole() {
        try {
            return reader.markSupported();
        } catch (Exception e) {
            return false;
        }
    }

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
                System.out.println("Enter album tracks:");
                builder.setTracks(Long.parseLong(reader.readLine().trim()));
                System.out.println("Enter album length:");
                builder.setLength(Long.parseLong(reader.readLine().trim()));
                System.out.println("Enter album sales:");
                builder.setSales(Float.parseFloat(reader.readLine().trim()));
                return Result.success(builder.createAlbum());
            }
        }catch (Exception e){
            return Result.failure(e, "Error while entering album");
        }
    }

    public Result <MusicBand> parseMusicBand(String[] parts) {
        try {
            return Result.success(new MusicBandBuilder()
                    .setId(Long.parseLong(parts[0]))
                    .setName(parts[1])
                    .setCoordinates(new Coordinates(Long.parseLong(parts[2]), Float.parseFloat(parts[3])))
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

    public Result<LocalDate> parseDate(String formattedDate) {
        try {
            return Result.success(LocalDate.parse(formattedDate, formatter));
        }catch (Exception e){
            return Result.failure(e,"Error with date format");
        }
    }

    public Result<Long> enterId() throws IOException {
        if (this.isConsole())
            System.out.println("Enter ID: ");
        try {
            return Result.success(Long.parseLong(reader.readLine().trim()));
        }catch (Exception e){
            return Result.failure(e, "Error with getting ID");
        }
    }

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
                        System.out.println("Вы ввели жанр не из списка, попробуйте ещё раз.");
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

    public Result<String> readLine() {
        try {
            return Result.success(reader.readLine());
        } catch (Exception e) {
            return Result.failure(e,"Error with reading");
        }
    }
}
