package com.nhlstenden.route;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nhlstenden.Main;
import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.MyArrayList;
import io.javalin.Javalin;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MainRoute
{
	public void configureRoutes(Javalin app) {
		app.get("/", ctx -> ctx.result("Hello World"));

		app.post("/upload", ctx -> {
			ctx.uploadedFiles("files").forEach(uploadedFile -> {
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try {
					MyArrayList<Object> resultArray = JsonUtils.jsonFileToMapGson(reader);
					SharedData<Object> data = new SharedData<>(resultArray);
					// Do something with resultArray...
					System.out.println(data.getString());
			//		System.out.println(data.getString());
//					TypeToken<List<Map<String, Object>>> token = new TypeToken<List<Map<String, Object>>>(){};
//					List<Map<String, Object>> dataList = new Gson().fromJson(reader, token.getType());
//
//					MyArrayList<Object> resultArray = new MyArrayList<>();
//					resultArray.addAll(dataList);
//
//					SharedData<Object> data = new SharedData<>(resultArray);
//					System.out.println(data.getString());

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}


}
