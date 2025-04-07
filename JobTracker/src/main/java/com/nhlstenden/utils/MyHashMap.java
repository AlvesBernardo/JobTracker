package com.nhlstenden.utils;

/**
 *
 */

import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @param <K>
 * @param <V>
 * @author downey
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V>
{

    // average number of entries per map before we rehash
    protected static final double FACTOR = 1.0;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Map<String, Integer> map = new MyHashMap<String, Integer>();
        for (int i = 0; i < 10; i++)
        {
            map.put(Integer.valueOf(i).toString(), i);
        }
        Integer value = map.get("3");
        System.out.println(value);
    }

    /**
     * Doubles the number of maps and rehashes the existing entries.
     */

    @Override
    public V put(K key, V value)
    {
        V oldValue = super.put(key, value);

        //System.out.println("Put " + key + " in " + map + " size now " + map.size());

        // check if the number of elements per map exceeds the threshold
        if (size() > maps.size() * FACTOR)
        {
            rehash();
        }
        return oldValue;
    }

    /**
     *
     */
    protected void rehash()
    {
        // save the existing entries
        List<MyLinearMap<K, V>> oldMaps = maps;

        // make more maps
        int newK = maps.size() * 2;
        makeMaps(newK);

        //System.out.println("Rehashing, n is now " + newN);

        // put the entries into the new map
        for (MyLinearMap<K, V> map : oldMaps)
        {
            for (Map.Entry<K, V> entry : map.getEntries())
            {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
}
