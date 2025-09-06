
import java.io.*;
import java.util.*;

public class MyMain2 {
    public static void main(String[] args) throws Exception {
        PersonList people = new PersonList();

        try (Scanner input = new Scanner(new File("resources/person.txt"))) {
            people.store(input);
        }

        people.display();

        System.out.println("Find ID=105 → index: " + people.find("105"));
        System.out.println("Find ID=999 → index: " + people.find("999"));
    }
}
