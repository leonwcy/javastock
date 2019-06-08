package stock.algo;

import java.util.*;

/**
 * Created by chenye.wu on 2018-03-14.
 */
public  abstract class AbstractMap<K,V> implements Map<K,V> {

    private transient volatile Set<K> keyCache;
    private transient volatile Collection<V> valueCache;

    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public int size(){
        return entrySet().size();
    }

    public boolean isEmpty(){
        return entrySet().isEmpty();
    }

    public boolean containsKey(Object key){
        return keySet().contains(key);
    }

    public boolean containsValue(Object value){return values().contains(value);}

    public V get(Object key){
        Iterator<Entry<K, V>> i = entrySet().iterator();

        while (i.hasNext())
        {
            if(i.next().getKey().equals(key))
            {
                return i.next().getValue();
            }
        }
        return null;
    }

//    public V put(final K key, final V value){
//        throw new UnsupportedOperationException();
//    }
    public abstract V put(final K key, final V value);

    public V remove(Object key){
        Iterator<Entry<K, V>> i = entrySet().iterator();
        V old = null;
        while (old == null && i.hasNext())
        {
            if(i.next().getKey().equals(key))
            {
                old = i.next().getValue();
                i.remove();
            }
        }
        return old;
    }

    public void putAll(Map<? extends K, ? extends V> m){
        for(Entry<? extends K, ? extends V> e:  m.entrySet()){
           put(e.getKey(),e.getValue());
        }
    }

    public void clear(){
        entrySet().clear();
    }


    public Set<K> keySet(){
        if(keyCache ==null)
        {
            keyCache = new AbstractSet<K>() {
                @Override
                public Iterator<K> iterator() {
                    final Iterator<Entry<K, V>> i = entrySet().iterator();

                    return new Iterator<K>() {
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsKey(o);
                }
            };
        }
        return keyCache;
    }

    public Collection<V> values(){
        if(valueCache==null)
        {
            valueCache = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    final Iterator<Entry<K, V>> i = entrySet().iterator();

                    return new Iterator<V>() {
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsValue(o);
                }
            };
        }
        return valueCache;
    }


    public abstract Set<Entry<K, V>> entrySet();

    public static class SimplyEntry<K,V>  implements Entry<K,V>{
        private final K _key;
        private V _value;

        public SimplyEntry(K key,V value)
        {
            _key = key;
            _value = value;
        }

        public K getKey() {
            return _key;
        }

        public V getValue() {
            return _value;
        }

        public V setValue(V value) {
            V oldValue = _value;
            _value = value;
            return oldValue;
        }

        public boolean equal(Object o)
        {
            if(!(o instanceof Map.Entry))
            {
                return false;
            }
            Map.Entry e = (Map.Entry)o;

            return eq(e.getKey(),this._key)&& eq(e.getValue(),this._value);
        }
    }
}
