package com.nhlstenden.controllers;

import com.nhlstenden.middelware.MyArrayList;

import java.util.Map;

public class BinarySearchUtil<T>
{
    public int binarySearch(SharedData<T> sharedData, String target, String key)
    {
        MyArrayList<T> arrayList = sharedData.getSharedArray();

        // Making sure the list is sorted before we start searching
        sortList(arrayList, key);

        int low = 0;
        int high = arrayList.size() - 1;
        target = target.toLowerCase();  // Normalize case for comparison

        while (low <= high)
        {
            int mid = low + (high - low) / 2;
            T midObject = arrayList.get(mid);
            String midValue = getPositionFromObject(midObject, key).toLowerCase();

            int comparison = midValue.compareTo(target);
            System.out.println("Low: " + low + ", Mid: " + mid + ", High: " + high + ", MidValue: " + midValue + ", Target: " + target + ", Comparison: " + comparison);
            if (comparison == 0) return mid;  // Found
            if (comparison < 0) low = mid + 1;
            else high = mid - 1;
        }

        // Check for partial match if exact match is not found
        for (int i = 0; i < arrayList.size(); i++)
        {
            T obj = arrayList.get(i);
            String value = getPositionFromObject(obj, key).toLowerCase();
            if (value.contains(target))
            {
                return i;  // Found a partial match
            }
        }
        return -1;  // Not found
    }

    private String getPositionFromObject(T object, String key)
    {
        if (object instanceof Map)
        {
            Map<String, Object> map = (Map<String, Object>) object;
            System.out.println("Retrieved value for key " + key + ": " + map.get(key));
            return (String) map.get(key);
        }
        return "";  // Return empty if no match
    }

    public void sortList(MyArrayList<T> arrayList, String key)
    {
        // Sort the list by the specified key (or whatever field you want to compare)
        arrayList.sort((a, b) -> getPositionFromObject(a, key).toLowerCase().compareTo(getPositionFromObject(b, key).toLowerCase()));
        System.out.println("Sorted list: " + arrayList);
    }
}
