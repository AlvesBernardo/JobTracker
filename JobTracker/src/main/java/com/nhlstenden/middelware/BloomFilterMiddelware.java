package com.nhlstenden.middelware;

import java.util.BitSet;
import java.util.function.Function;

public class BloomFilterMiddelware<T>
{
    private final BitSet bitSet;
    private final Function<T, Integer>[] hashFunction;

    public BloomFilterMiddelware(int size, Function<T, Integer>[] hashFunction)
    {
        this.bitSet = new BitSet();
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
