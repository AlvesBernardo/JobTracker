package com.nhlstenden.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhlstenden.middelware.MyArrayList;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils
{
	public static MyArrayList<Object> jsonFileToMapGson(Reader reader) throws IOException {
		Gson gson = new Gson();
		List<Object> list = gson.fromJson(reader, new TypeToken<List<Object>>() {}.getType());
		MyArrayList<Object> myArrayList = new MyArrayList<>();
		myArrayList.addAll(list);
		return myArrayList;
	}
}
