package Status;

public enum ClothesType {
    ForBody,
    ForHead,
    ForLegs,
    ForFeet,
    ForHands,
    ForArms;


    @Override
    public String toString() {
        return "Status.ClothesType{" +
                "type=" + this.name() +
                '}';
    }

}
