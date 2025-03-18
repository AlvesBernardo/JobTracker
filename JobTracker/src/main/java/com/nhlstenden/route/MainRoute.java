package com.nhlstenden.route;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nhlstenden.Main;
import com.nhlstenden.controllers.BinarySearchUtil;
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
	private SharedData<Object> data;
	public void configureRoutes(Javalin app) {
		app.get("/", ctx -> ctx.result("Hello World"));

		app.post("/upload", ctx -> {
			ctx.uploadedFiles("files").forEach(uploadedFile -> {
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try {
					MyArrayList<Object> resultArray = JsonUtils.jsonFileToMapGson(reader);
					this.data = new SharedData<>(resultArray);
					System.out.println("Uploaded and parsed");
					ctx.status(200).result("Completed");
//					// Do something with resultArray...
//					System.out.println(data.getString());
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
					ctx.status(500).result("Error processing");
				}
			});
		});


		app.get("/binarySearch", ctx -> {
			BinarySearchUtil<Object> searchUtil = new BinarySearchUtil<>();
			String targetPosition = ctx.body();// Read raw string from query parameter
			if (targetPosition.isEmpty()) {
				ctx.status(400).result("Invalid search request. A job title is required.");
				return;
			}
			int resultIndex = searchUtil.binarySearch(data, targetPosition, "position");

			if (resultIndex != -1) {
				Object foundJob = data.getSharedArray().get(resultIndex);
				ctx.json(foundJob);
				System.out.println("Found job: " + foundJob);
			} else {
				ctx.status(404).result("Job not found.");
				System.out.println("Job not found.");
			}
		});;
	}


}
