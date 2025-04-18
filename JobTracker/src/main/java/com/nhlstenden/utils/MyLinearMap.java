/**
 *
 */
package com.nhlstenden.utils;

import java.util.*;

/**
 * Implementation of a Map using a List of entries, so most
 * operations are linear time.
 *
 * @param <K>
 * @param <V>
 * @author downey
 */
public class MyLinearMap<K, V> implements Map<K, V>
{

    private final List<Entry> entries = new ArrayList<Entry>();

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Map<String, Integer> map = new MyLinearMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key : map.keySet())
        {
            System.out.println(key + ", " + map.get(key));
        }
    }

    @Override
    public void clear()
    {
        entries.clear();
    }

    @Override
    public boolean containsKey(Object target)
    {
        return findEntry(target) != null;
    }

    /**
     * Returns the entry that contains the target key, or null if there is none.
     *
     * @param target
     */
    private Entry findEntry(Object target)
    {
        for (Entry entry : entries)
        {
            if (equals(target, entry.getKey()))
            {
                return entry;
            }
        }
        return null;
    }

    /**
     * Compares two keys or two values, handling null correctly.
     *
     * @param target
     * @param obj
     * @return
     */
    private boolean equals(Object target, Object obj)
    {
        if (target == null)
        {
            return obj == null;
        }
        return target.equals(obj);
    }

    @Override
    public boolean containsValue(Object target)
    {
        for (Map.Entry<K, V> entry : entries)
        {
            if (equals(target, entry.getValue()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>(entries);
        return entrySet;
    }

    @Override
    public V get(Object key)
    {
        Entry entry = findEntry(key);
        if (entry == null)
        {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public boolean isEmpty()
    {
        return entries.isEmpty();
    }

    @Override
    public Set<K> keySet()
    {
        Set<K> set = new HashSet<K>();
        for (Entry entry : entries)
        {
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public V put(K key, V value)
    {
        Entry entry = findEntry(key);
        if (entry == null)
        {
            entries.add(new Entry(key, value));
            return null;
        } else
        {
            V oldValue = entry.getValue();
            entry.setValue(value);
            return oldValue;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map)
    {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet())
        {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key)
    {
        Entry entry = findEntry(key);
        if (entry == null)
        {
            return null;
        } else
        {
            V value = entry.getValue();
            entries.remove(entry);
            return value;
        }
    }

    @Override
    public int size()
    {
        return entries.size();
    }

    @Override
    public Collection<V> values()
    {
        Set<V> set = new HashSet<V>();
        for (Entry entry : entries)
        {
            set.add(entry.getValue());
        }
        return set;
    }

    /**
     * Returns a reference to `entries`.
     * <p>
     * This is not part of the Map interface; it is here to provide the functionality
     * of `entrySet` in a way that is substantially simpler than the "right" way.
     *
     * @return
     */
    protected Collection<? extends java.util.Map.Entry<K, V>> getEntries()
    {
        return entries;
    }

    public class Entry implements Map.Entry<K, V>
    {
        private final K key;
        private V value;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public V setValue(V newValue)
        {
            value = newValue;
            return value;
        }
    }
}
