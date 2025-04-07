package com.nhlstenden.middelware;

public class MyHashTable<K, V>
{

    private final Entry<K, V>[] table;
    private final int capacity = 16;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashTable()
    {
        table = new Entry[capacity];
    }

    private int getIndex(K key)
    {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value)
    {
        int index = getIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null)
        {
            table[index] = newEntry;
            size++;
        } else
        {
            Entry<K, V> current = table[index];
            Entry<K, V> prev = null;
            while (current != null)
            {
                if (current.key.equals(key))
                {
                    current.value = value;
                    return;
                }
                prev = current;
                current = current.next;
            }
            prev.next = newEntry;
            size++;
        }
    }

    public V get(K key)
    {
        int index = getIndex(key);
        Entry<K, V> current = table[index];

        while (current != null)
        {
            if (current.key.equals(key))
            {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key)
    {
        return get(key) != null;
    }

    public V remove(K key)
    {
        int index = getIndex(key);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null)
        {
            if (current.key.equals(key))
            {
                if (prev == null)
                {
                    table[index] = current.next;
                } else
                {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public int size()
    {
        return size;
    }

    public V getByIndex(int index)
    {
        int count = 0;
        for (Entry<K, V> bucket : table)
        {
            Entry<K, V> current = bucket;
            while (current != null)
            {
                if (count == index)
                {
                    return current.value;
                }
                count++;
                current = current.next;
            }
        }
        return null;
    }

    private static class Entry<K, V>
    {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }

}
