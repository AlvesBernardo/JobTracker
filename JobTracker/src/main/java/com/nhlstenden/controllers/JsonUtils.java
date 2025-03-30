package com.nhlstenden.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhlstenden.middelware.MyArrayList;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils
{
    public static <T> MyArrayList<T> jsonFileToMapGson(Reader reader, Type type) throws IOException
    {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(reader, type);
        MyArrayList<T> myArrayList = new MyArrayList<>();
        myArrayList.addAll(list);
        return myArrayList;
    }
}