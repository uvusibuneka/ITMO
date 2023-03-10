package Status;

public enum Frying {
    RAW("Raw"),
    MEDIUM("Medium"),
    FRIED("Fried"),
    BURNT("Burnt");

    private String replic;

    Frying(String replic) {
        this.replic = replic;
    }

    @Override
    public String toString() {
        return replic;
    }
}