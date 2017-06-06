package hashtable;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasilii97
 */
public class HashTableTest {
    
    @Test
    public void testGet() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        String value = table.get(10);
        if (value == null || !value.equals("Cat")) {
            fail("Error");
        }
        value = table.get(20);
        if (value != null) {
            fail("Error");
        }
        table.put(20, "Dog");
        value = table.get(20);
        if (value == null || !value.equals("Dog")) {
            fail("Error");
        }
        value = table.put(20, "Alligator");
        if (value == null || !value.equals("Dog")) {
            fail("Error");
        }
    }

    @Test
    public void testPut() {
        HashTable<Integer, String> table = new HashTable<>();
        String value = table.put(10, "Alex");
        if (value != null) {
            fail("Error");
        }
        value = table.put(10, "Alexey");
        if (!value.equals("Alex")) {
            fail("Error");
        }
        value = table.put(20, "Vasya");
        if (value != null) {
            fail("Error");
        }
    }

    @Test
    public void testContainsValue() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        if (!table.containsValue("Cat")) {
            fail("Error");
        }
        if (table.containsValue("Dog")) {
            fail("Error");
        }
    }

    @Test
    public void testContainsKey() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        if (!table.containsKey(10)) {
            fail("Error");
        }
        if (table.containsKey(11)) {
            fail("Error");
        }
    }

    @Test
    public void testRemove() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        table.put(1, "Dog");
        table.put(100, "Fox");
        table.put(2110, "Fish");
        table.remove(1);
        if (table.containsKey("Dog")) {
            fail("Error");
        }
        table.remove(2110);
        if (table.containsKey("Fish")) {
            fail("Error");
        }
        table.remove(100);
        if (table.containsKey("Fox")) {
            fail("Error");
        }
    }

    @Test
    public void testClear() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        table.put(1, "Dog");
        table.put(100, "Fox");
        table.put(2110, "Fish");
        table.clear();
        if (table.containsValue("Cat") || table.containsValue("Dog") || table.containsValue("Fox") || table.containsValue("Fish")) {
            fail("Error");
        }
    }

    @Test
    public void testKeySet() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(10, "Cat");
        table.put(1, "Dog");
        table.put(100, "Fox");
        table.put(2110, "Fish");
        Set<Integer> setAcc = table.keySet();
        Set<Integer> setExp = new TreeSet<>(Arrays.asList(10, 1, 100, 2110));
        assertArrayEquals(setExp.toArray(), setAcc.toArray());
    }

    public void testIsEmpty() {
        HashTable<Integer, String> table = new HashTable<>();
        if (!table.isEmpty()) {
            fail("Error");
        }
        table.put(1, "A");
        table.put(2, "B");
        table.put(3, "C");
        table.put(4, "Unexpected value");
        if (table.isEmpty()) {
            System.out.println("Error");
        }
        table.remove(1);
        table.remove(2);
        table.remove(3);
        table.remove(4);
        if (!table.isEmpty()) {
            System.out.println("Error");
        }
        table.put(1, "Hello");
        table.clear();
        if (!table.isEmpty()) {
            System.out.println("Error");
        }
    }

    @Test
    public void testSize() {
        HashTable<Integer, String> table = new HashTable<>();
        int expSize = 3;
        table.put(1, "A");
        table.put(2, "B");
        table.put(3, "C");
        table.put(4, "Unexpected value");
        table.remove(2);
        if (expSize != table.size()) {
            fail("Error");
        }
    }

    @Test
    public void testValues() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(1, "A");
        table.put(3, "B");
        table.put(2, "C");
        String[] exp = new String[]{"A", "C", "B"};
        assertArrayEquals(exp, table.values().toArray());
    }
    
}
