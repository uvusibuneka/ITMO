import Status.*;
import java.util.ArrayList;
import java.util.Objects;


public class Subject extends Entity {
    protected Roasting frying = new Roasting();
    protected Color color = new Color();
    {
        frying.setRoasting(Frying.RAW);
        color.setColor(ColorType.CLEAR);
    }
    protected boolean possibilityToSit;

    public Subject(String name, boolean possibilityToSit, Location cord) {
        super(name, cord);
        this.possibilityToSit = possibilityToSit;
    }

    public Subject(String name, boolean possibilityToSit) {
        super(name);
        this.possibilityToSit = possibilityToSit;
    }

    public Subject(String name) {
        super(name);
    }


    class Hole {
        private int volume;

        public Hole(int volume) {
            this.volume = volume;
        }
        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        @Override
        public String toString() {
            return "Hole{" + "volume=" + volume + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Hole hole = (Hole) o;
            return volume == hole.volume;
        }

        @Override
        public int hashCode() {
            return Objects.hash(volume);
        }
    }

    protected ArrayList<Hole> holes = new ArrayList<Hole>();

    public void addHole(Hole hole) {
        holes.add(hole);
        System.out.println("В " + this.getName() + " находится отверстие объемом " + hole.getVolume());
    }

    public Hole getHole(int x){
        return holes.get(x);
    }

    public void removeHole(int x){
        holes.remove(x);
    }

    public int getHolesCount(){
        return holes.size();
    }
}

