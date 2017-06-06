package hashtable;

import java.util.Collection;

/**
 *
 * @author Vasilii97
 */
public class Main {

    public static void main(String[] args) {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(1, "Cat");
        table.put(2000, "Dog");
        table.put(3456, "Alligator");
        table.put(1, "Kitty");
        Collection<String> animals = table.values();
        for(String s: animals)
        {
            System.out.println(s);
        }
    }

}
