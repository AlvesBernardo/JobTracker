package com.nhlstenden.middelware;


import java.util.BitSet;
import java.util.function.Function;

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

    public void set(T element)
    {
        add(element);
    }
    //having a delete method is not needed in a bloom filter
    // its a probabilistic data structure
    //it does not support deletion
    // it can have false positives but not false negatives


}
