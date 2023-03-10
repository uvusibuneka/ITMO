package music;

public class Album {

    static final float epsilon = 0.01f;

    private String name;
    private long tracks;
    private Long length;
    private Float sales;

    public Album(String name, long tracks, Long length, Float sales) {
        this.name = name;
        this.tracks = tracks;
        this.length = length;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public long getTracks() {
        return tracks;
    }

    public Long getLength() {
        return length;
    }

    public Float getSales() {
        return sales;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTracks(long tracks) {
        this.tracks = tracks;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public void setSales(Float sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "MusicClasses.Album{" +
                "name='" + name + '\'' +
                ", tracks=" + tracks +
                ", length=" + length +
                ", sales=" + sales +
                '}';
    }

    public int compareTo(Album bestAlbum) {
        int result = this.name.compareTo(bestAlbum.name);
        if (result != 0) {
            return result;
        }

        result = Long.compare(this.tracks, bestAlbum.tracks);
        if (result != 0) {
            return result;
        }

        result = Long.compare(this.length, bestAlbum.length);
        if (result != 0) {
            return result;
        }

        if (Math.abs(this.sales - bestAlbum.sales) < epsilon) {
            return 0;
        } else if (this.sales < bestAlbum.sales) {
            return -1;
        } else {
            return 1;
        }

    }
}
