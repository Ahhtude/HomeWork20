import lombok.Getter;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Getter
public class SimpleHashMap<K,V> implements IHashStorage<K,V> {
    private int capasity = 16;
    private int size;
    private int modificationCount;

    @SuppressWarnings("unchecked")
    private SimpleEntry<K,V>[] array = (SimpleEntry<K,V>[])new SimpleEntry[capasity];

    public SimpleHashMap() {}


    @Override
    public boolean add(K key, V value) {
        modificationCount++;
        if(key == null) {
            if(array[0] == null) {
                array[0] = new SimpleEntry<K, V>(null, value);
                size++;
            } else {
                array[0].setValue(value);
            }
        }

        int numBacket = Math.abs(key.hashCode()) % capasity;

        if(array[numBacket] == null) {
            array[numBacket] = new SimpleEntry<K,V>(key, value);
            size++;
        } else {
            SimpleEntry<K,V> entry = array[numBacket];

            while(true) {
                if(entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return true;
                }

                if(entry.getNext() == null) {
                    break;
                }
                entry = entry.getNext();
            }

            entry.setNext(new SimpleEntry<K,V>(key, value));
            size++;
        }
        return true;
    }

    @Override
    public V get(K key) {

        if(key == null) {
            if(array[0] == null) {
                return null;
            }

            return array[0].getValue();
        }

        int numBacket = Math.abs(key.hashCode()) % capasity;

        if(array[numBacket] == null) {
            return null;
        } else {
            SimpleEntry<K,V> entry = array[numBacket];

            while(true) {
                if(entry.getKey().equals(key)) {
                    return entry.getValue();
                }

                if(entry.getNext() == null) {
                    return null;
                }

                entry = entry.getNext();
            }
        }
    }

    public void remove(K key) {
        if(key == null) {
            if(array[0] != null) {
                modificationCount++;
                size--;

                if(array[0].getNext() != null) {
                    array[0] = array[0].getNext();
                }
            }
            return;
        }

        int numBacket = Math.abs(key.hashCode()) % capasity;

        if(array[numBacket] != null) {
            if(array[numBacket].getKey().equals(key)) {
                array[numBacket] = array[numBacket].getNext();
                modificationCount++;
                size--;
                return;
            }

            if(array[numBacket].getNext() != null) {
                SimpleEntry<K,V> prev = array[numBacket];
                SimpleEntry<K,V> entry = array[numBacket].getNext();
                SimpleEntry<K,V> next = entry.getNext();

                while(true) {
                    if(entry.getKey().equals(key)) {
                        modificationCount++;
                        size--;

                        prev.setNext(next);
                        return;
                    }

                    if(next == null) {
                        return;
                    }

                    prev = entry;
                    entry = next;
                    next = next.getNext();
                }
            }
        }
    }


    public Set<SimpleEntry<K,V>> entrySet() {
        Set<SimpleEntry<K,V>> set = new HashSet<>();

        for(SimpleEntry<K,V> entry : array) {
            while(entry != null) {
                set.add(entry);
                entry = entry.getNext();
            }
        }

        return set;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        for(SimpleEntry<K,V> entry : array) {
            while(entry != null) {
                set.add(entry.getKey());
                entry = entry.getNext();
            }
        }

        return set;
    }
}