package stock.algo;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenye.wu on 2018-03-15.
 */
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V> {
    transient Entry<K,V>[] table = null;
    private final int defaultCapital = 3;

    public HashMap(){
        table = new Entry[defaultCapital];
    }

    final int hash(Object k) {
        int h = 0;
        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int indexFor(int h, int length) {
        return h & (length-1);
    }

    public V get(Object key){
        int h = hash(key);
        int idx = indexFor(h,table.length-1);

        Entry<K,V> cur = table[idx];
        if(cur ==null)
        {
            return null;
        }else {
            if (cur.getKey()!=null && cur.getKey().equals(key)) {
                return cur.getValue();
            }
            while (cur!=null) {
                if (cur.getKey().equals(key)) {
                    return cur.getValue();
                }
                cur = cur.getNext();
            }
        }
        return null;
    }

    public V put(K key, V value) {
        int h = hash(key);
        int idx = indexFor(h,table.length-1);

        Entry<K,V> cur = table[idx];
        V oldValue = null;
        if(cur ==null)
        {
            table[idx] = new Entry<K, V>(key,value);
        }else{
            if(cur.getKey()!=null && cur.getKey().equals(key))
            {
                oldValue = cur.getValue();
                cur.setValue(value);

                return oldValue;
            }
            while (cur.hasNext())
            {
                if(cur.getKey().equals(key))
                {
                    oldValue = cur.getValue();
                    cur.setValue(value);

                    return oldValue;
                }
                cur = cur.getNext();
            }
            cur.next =  new Entry<K, V>(key,value);
        }

        return oldValue;
    }

    private void addEntry(Entry<K,V> o,Entry<K,V> n)
    {
        Entry<K,V> cur = o;
        while (cur.hasNext())
        {
            cur = cur.getNext();
        }
        cur.next = n;
    }

    private static class Entry<K,V> implements Map.Entry<K,V> {
        private K _key;
        private V _value;
        public Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> n){
            _key = key;
            _value = value;
            next = n;
        }

        public Entry(K key, V value){
            _key = key;
            _value = value;
        }

        public Entry<K,V> withNext(Entry<K,V> n){
            next = n;
            return this;
        }

        public K getKey() {
            return _key;
        }

        public V getValue() {
            return _value;
        }

        public Entry<K,V> getNext(){
            return next;
        }

        public boolean hasNext(){
            return next!=null;
        }

        public V setValue(V value) {
            V oldValue = _value;
            _value = value;
            return oldValue;
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return entrySet != null? entrySet: new EntrySet();
    }

    private transient Set<Map.Entry<K,V>> entrySet = null;

    private final class EntrySet extends AbstractSet<Map.Entry<K,V>> {

        public Iterator<Map.Entry<K, V>> iterator() {
            return null;
        }

        public int size() {
            return 0;
        }
    }

    private class HashIterator<E> implements Iterator<E> {


        public boolean hasNext() {
            return false;
        }

        public E next() {
            return null;
        }

        public void remove() {

        }
    }
}
