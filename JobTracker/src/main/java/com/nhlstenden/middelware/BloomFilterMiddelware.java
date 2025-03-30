package com.nhlstenden.middelware;


import com.google.gson.Gson;
import com.nhlstenden.utils.MyHashMap;

import java.util.List;
import java.util.function.Function;
import java.util.*;

public class BloomFilterMiddelware<T>
{
    private final BitSet bitSet;
    private final Function<T, Integer>[] hashFunction;

    public BloomFilterMiddelware(int size, Function<T, Integer>[] hashFunction)
    {
        this.bitSet = new BitSet(size);
        this.hashFunction = hashFunction;
    }

    public void add(T element)
    {
        for (Function<T, Integer> hashFunction : hashFunction)
        {
            int hash = hashFunction.apply(element);
            bitSet.set(Math.abs(hash) % bitSet.size(), true);
        }
    }

    public boolean mightContain(T element)
    {
        for (Function<T, Integer> hashFunction : hashFunction)
        {
            int hash = hashFunction.apply(element);
            if (!bitSet.get(Math.abs(hash) % bitSet.size()))
            {
                return false;
            }
        }
        return true;
    }

}
