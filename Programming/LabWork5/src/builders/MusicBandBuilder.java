package builders;

import music.Album;
import music.Coordinates;
import music.MusicBand;
import music.MusicGenre;

import java.time.LocalDate;

public class MusicBandBuilder {

    private long id;
    private String name;
    private Coordinates coordinates;
    private Long numberOfParticipants;
    private MusicGenre genre;
    private Album bestAlbum;

    private LocalDate creationDate;

    public MusicBandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MusicBandBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MusicBandBuilder setNumberOfParticipants(Long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
        return this;
    }

    public MusicBandBuilder setGenre(MusicGenre genre) {
        this.genre = genre;
        return this;
    }

    public MusicBandBuilder setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
        return this;
    }

    public MusicBandBuilder setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public MusicBand createMusicBand() {
        return new MusicBand(name, coordinates, creationDate, numberOfParticipants, genre, bestAlbum);
    }

    public MusicBandBuilder setId(long id) {
        this.id = id;
        return this;
    }
}
