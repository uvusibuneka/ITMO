import java.util.Objects;

public class Lamp extends Subject {
    protected boolean isOn;
    protected int brightness;

    public Lamp(String name) {
        super(name);
        this.isOn = false;
        this.brightness = 122;
    }

    public Lamp(String name, int brightness) {
        super(name);
        this.isOn = false;
        this.brightness = 122;
    }

    public void turnOn() {
        this.isOn = true;
    }

    public void turnOff() {
        this.isOn = false;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public int getBrightness() {
        return this.brightness;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "isOn=" + isOn +
                ", brightness=" + brightness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lamp lampBulb = (Lamp) o;
        return isOn == lampBulb.isOn &&
                brightness == lampBulb.brightness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOn, brightness);
    }

}