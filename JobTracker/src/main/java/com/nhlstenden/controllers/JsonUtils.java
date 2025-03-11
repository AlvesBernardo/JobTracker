package com.nhlstenden.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils
{
	public static ArrayList<Object> jsonFileToMapGson(Reader reader) throws IOException {
		Gson gson = new Gson();
		return gson.fromJson(reader,  new TypeToken<List<Object>>() {}.getType());
	}
}
