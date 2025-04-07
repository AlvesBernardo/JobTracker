package com.nhlstenden.middelware;

import com.nhlstenden.controllers.BinarySearchUtil;
import com.nhlstenden.controllers.SharedData;

import java.util.Map;

public class BinaryMiddelWare<T>
{
    public Object doBinarySearch(SharedData<T> data, Map<String, String> requestBody)
    {
        BinarySearchUtil<T> searchUtil = new BinarySearchUtil<>();
        String key = requestBody.get("key");
        String value = requestBody.get("value");

        value = value.trim();

        System.out.println("value " + value);

        int resultIndex = searchUtil.binarySearch(data, value, key);

        if (resultIndex != -1)
        {
            return data.getSharedArray().get(resultIndex);
        }
        return null;
    }
}
