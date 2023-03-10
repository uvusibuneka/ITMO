public class Shorty extends Person {
    public Shorty(String name) {
        super(name);
    }


    public void jugglingForCooling(Potato potato) {
        System.out.println(this.getName() + " перебрасывает " + potato.getName() + " в руках, чтобы остудить");
    }

    public void playRashibalochka() {
        System.out.println(this.getName() + " играет в расшибалочку");
    }

    public void tellSadHistory() {
        System.out.println(this.getName() + " рассказывает грустную историю");
    }

    public void toSit(Subject subject) {
        // место для исключения!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        System.out.println(this.getName() + " сел на " + subject.getName());
    }

    public void toStandUp(Subject subject) {
        System.out.println(this.getName() + " встал с " + subject.getName());
    }


}
