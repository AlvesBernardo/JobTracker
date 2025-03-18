package com.nhlstenden.route;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nhlstenden.Main;
import com.nhlstenden.controllers.BinarySearchUtil;

import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.priorityQueue.ApplicationPriorityQueue;
import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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
          					ApplicationPriorityQueue applicationPriorityQueue = new ApplicationPriorityQueue(data.getSharedArray(), 0);
					// Do something with resultArray...
					System.out.println(data);
					System.out.println((applicationPriorityQueue.peek()));
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
			Map<String, String> requestBody = new Gson().fromJson(ctx.body(), Map.class);
			String key = requestBody.get("key");
			String value = requestBody.get("value");

			if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty()) {
				ctx.status(400).result("Invalid search request. Both key and value are required.");
				return;
			}
			value = value.trim();

			int resultIndex = searchUtil.binarySearch(data, value, key);

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


        app.get("/application/{id}", ctx ->
        {
            String id = ctx.pathParam("id");
            Object application = jobApplicationService.getApplication(id);
            if (application != null)
            {
                ctx.json(application);
            } else
            {
                ctx.status(404).result("Application not found.");
            }
        });
    }
}
