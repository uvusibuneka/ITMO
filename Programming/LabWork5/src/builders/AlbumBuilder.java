package builders;

import music.Album;

public class AlbumBuilder {
    private String name;
    private long tracks;
    private Long length;
    private Float sales;

    public AlbumBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AlbumBuilder setTracks(long tracks) {
        this.tracks = tracks;
        return this;
    }

    public AlbumBuilder setLength(Long length) {
        this.length = length;
        return this;
    }

    public AlbumBuilder setSales(Float sales) {
        this.sales = sales;
        return this;
    }

    public Album createAlbum() {
        return new Album(name, tracks, length, sales);
    }
}
