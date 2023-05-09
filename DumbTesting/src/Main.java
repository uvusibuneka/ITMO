/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String t = reader.readLine();
        if(t == null) {
            System.out.println("Console is not available");
            reader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            System.out.println("Console is available");
        }
        System.out.println(t);
        t = reader.readLine(); // <-- here is the problem, nothing is getting read
        System.out.println(t);
    }
}*/
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<ParentClass> arrl1 = new ArrayList<>();
        ArrayList<ChildClass> arrl2 = new ArrayList<>();

        arrl1 = arrl2;
        arrl2 = arrl1;
    }
}
