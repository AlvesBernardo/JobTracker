package com.nhlstenden.controllers;

import com.google.gson.Gson;
import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.utils.MyHashMap;

import java.util.*;

public class SharedData<T>
{
    private MyArrayList<T> sharedArray;

    public SharedData(MyArrayList<T> data)
    {
        this.sharedArray = data;
    }

    public MyArrayList<T> getSharedArray()
    {
        return this.sharedArray;
    }

    public void setSharedArray(MyArrayList<T> sharedArray)
    {
        this.sharedArray = sharedArray;
    }

    public MyArrayList<T> asList()
    {
        return sharedArray;
    }

    public Queue<T> asQueue()
    {
        return new LinkedList<>(sharedArray);
    }

    public int getSize()
    {
        return this.sharedArray.size();

    }

    public String getString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int lenght = sharedArray.size();

        for (int i = 0; i < lenght; i++)
        {
            T elements = sharedArray.get(i);
            sb.append(elements.toString());
            if (i < lenght - 1)
            {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void sortByDate()
    {
        Collections.sort(this.sharedArray, new Comparator<T>()
        {
            @Override
            public int compare(T o1, T o2)
            {
                if (o1 instanceof MyHashMap && o2 instanceof MyHashMap)
                {
                    MyHashMap<String, T> map1 = (MyHashMap<String, T>) o1;
                    MyHashMap<String, T> map2 = (MyHashMap<String, T>) o2;
                    Date date1 = new Gson().fromJson(map1.get("date_applied").toString(), Date.class);
                    Date date2 = new Gson().fromJson(map2.get("date_applied").toString(), Date.class);
                    return date2.compareTo(date1); // Sort by newest date first
                }
                return 0;
            }
        });
    }
}
