import java.util.*;

public class PersonList {
    private LinkedList<Person> list;

    public PersonList() {
        list = new LinkedList<>();
    }

    public void store(Scanner input) {
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

    public void display() {
        for (Person p : list) {
            System.out.println(p);
        }
    }

    public int find(String sid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(sid)) {
                return i;
            }
        }
        return -1;
    }
}
