package hashtable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Vasilii97
 */
public class HashTable<K, V> implements Map<K, V> {

    private static class Record<K, V> implements Entry<K, V> {

        private V value;
        private K key;
        public Record<K, V> next;

        public Record(V value, K key) {
            this.value = value;
            this.key = key;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

    }

    private Record<?, ?>[] table;
    private int size;
    private float loadFactor;
    private int capacity;
    private int threshold;

    public HashTable() {
        this(11, 0.75f);
    }

    public HashTable(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public HashTable(int initialCapacity, float loadFactor) {
        table = new Record<?, ?>[initialCapacity];
        capacity = initialCapacity;
        size = 0;
        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);
    }

    private int hash(int hash) {
        return hash % capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = hash(key.hashCode());
        for (Record<?, ?> r = table[index]; r != null; r = r.next) {
            if (r.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            for (Record<?, ?> r = table[i]; r != null; r = r.next) {
                if (r.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = hash(key.hashCode());
        for (Record<?, ?> r = table[index]; r != null; r = r.next) {
            if (r.getKey().equals(key)) {
                return (V) r.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key.hashCode());
        if (table[index] == null) {
            table[index] = new Record<>(value, key);
            size++;
            if (size > threshold) {
                rehash();
            }
            return null;
        }
        Record<K, V> oldRecord = (Record<K, V>) table[index];
        while (oldRecord.next != null) {
            if (oldRecord.key.equals(key)) {
                V oldValue = oldRecord.value;
                oldRecord.value = value;
                return oldValue;
            }
            oldRecord = oldRecord.next;
        }
        if (oldRecord.key.equals(key)) {
            V oldValue = oldRecord.value;
            oldRecord.value = value;
            return oldValue;
        }
        oldRecord.next = new Record(value, key);
        size++;
        if (size > threshold) {
            rehash();
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = hash(key.hashCode());
        if (table[index] == null) {
            return null;
        }
        Record<K, V> record = (Record<K, V>) table[index];
        if (record.getKey().equals(key)) {
            V value = record.getValue();
            table[index] = record.next;
            size--;
            return value;
        }
        Record<K, V> prevRecord = record;
        while (record.next != null) {
            record = record.next;
            if (record.key.equals(key)) {
                V value = record.value;
                prevRecord.next = record.next;
                size--;
                return value;
            }
            prevRecord = record;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        for (int i = 0; i < capacity; i++) {
            for (Record<?, ?> r = table[i]; r != null; r = r.next) {
                set.add((K) r.getKey());
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            for (Record<?, ?> r = table[i]; r != null; r = r.next) {
                list.add((V) r.getValue());
            }
        }
        return list;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new TreeSet<>();
        for (int i = 0; i < capacity; i++) {
            for (Record<?, ?> r = table[i]; r != null; r = r.next) {
                set.add((Entry<K, V>) r);
            }
        }
        return set;
    }

    private void rehash() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity * 2;

        if (newCapacity - Integer.MAX_VALUE > 0) {
            if (oldCapacity == Integer.MAX_VALUE) {
                return;
            }
            newCapacity = Integer.MAX_VALUE;
        }

        Record<K, V>[] oldMap = (Record<K, V>[]) table;
        Record<K, V>[] newMap = new Record[newCapacity];
        threshold = (int) Math.min(newCapacity * loadFactor, Integer.MAX_VALUE);

        table = newMap;
        for (int i = oldCapacity - 1; i > 0; i--) {
            for (Record<K, V> old = oldMap[i]; old != null;) {
                Record<K, V> cur = old;
                old = old.next;

                int index = cur.getKey().hashCode() % newCapacity;
                cur.next = newMap[index];
                newMap[index] = cur;
            }
        }

    }

}
