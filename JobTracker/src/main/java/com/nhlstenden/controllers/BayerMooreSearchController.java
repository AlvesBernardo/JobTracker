package com.nhlstenden.controllers;

import com.nhlstenden.middelware.MyArrayList;

import java.util.Locale;
import java.util.Map;

public class BayerMooreSearchController<T>
{

    public MyArrayList<Map<String, T>> searchByKey(MyArrayList<T> data, String key, String pattern)
    {
        MyArrayList<Map<String, T>> results = new MyArrayList<>();
        if (data == null || pattern == null || key == null) return results;

        for (int i = 0; i < data.size(); i++)
        {
            T item = data.get(i);
            if (item instanceof Map)
            {
                Map<String, T> entry = (Map<String, T>) item;
                T value = entry.get(key);
                if (value != null)
                {
                    String fieldValue = value.toString().toLowerCase(Locale.ROOT);
                    String searchPattern = pattern.toLowerCase(Locale.ROOT);

                    if (!BayerMooreStringSearch.search(fieldValue, searchPattern).isEmpty())
                    {
                        results.add(entry);
                    }
                }
            }
        }
        return results;
    }
}
