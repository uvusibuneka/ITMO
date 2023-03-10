package music;

import java.time.LocalDate;

public class MusicBand implements Comparable<MusicBand> {
    private static long idCounter = 0;
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long numberOfParticipants;
    private MusicGenre genre;
    private Album bestAlbum;

    public MusicBand(String name, Coordinates coordinates, LocalDate creationDate, long numberOfParticipants, MusicGenre genre, Album bestAlbum) {
        this.id = ++idCounter;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(long id, String name, Coordinates coordinates, LocalDate creationDate, long numberOfParticipants, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        idCounter = Math.max(idCounter, id);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public static void setIdCounter(long idCounter) {
        MusicBand.idCounter = idCounter;
    }

    public MusicBand(String name) {
        this.name = name;
        this.id = ++idCounter;
    }

    public static long getIdCounter() {
        return idCounter;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    @Override
    public int compareTo(MusicBand other) {
        int result = this.name.compareTo(other.name);
        if (result != 0) {
            return result;
        }

        result = this.coordinates.compareTo(other.coordinates);
        if (result != 0) {
            return result;
        }

        result = this.creationDate.compareTo(other.creationDate);
        if (result != 0) {
            return result;
        }

        result = Long.compare(this.numberOfParticipants, other.numberOfParticipants);
        if (result != 0) {
            return result;
        }

        result = this.genre.compareTo(other.genre);
        if (result != 0) {
            return result;
        }

        result = this.bestAlbum.compareTo(other.bestAlbum);
        if (result != 0) {
            return result;
        }

        return 0;
    }
    @Override
    public String toString() {
        return "MusicClasses.MusicBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                '}';
    }
}