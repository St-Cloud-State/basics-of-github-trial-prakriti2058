import java.io.*;
import java.util.*;

public class MyMain {

    public static void store(Scanner input, LinkedList<Person> list) {
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length == 3) {
                Person p = new Person(parts[0].trim(), parts[1].trim(), parts[2].trim());
                list.add(p);
            }
        }
    }

    public static void display(PrintStream out, LinkedList<Person> list) {
        for (Person p : list) {
            out.println(p);
        }
    }

    public static int find(String sid, LinkedList<Person> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(sid)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java MyMain <filename>");
            return;
        }

        String filename = args[0];  
        LinkedList<Person> people = new LinkedList<>();

        try (Scanner input = new Scanner(new File(filename))) {
            store(input, people);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
            return;
        }

        display(System.out, people);

        System.out.println("Find ID=102 → index: " + find("102", people));
        System.out.println("Find ID=999 → index: " + find("999", people));
    }
}
