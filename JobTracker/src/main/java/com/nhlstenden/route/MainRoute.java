package com.nhlstenden.route;


import com.nhlstenden.Main;
import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.priorityQueue.ApplicationPriorityQueue;
import io.javalin.Javalin;
import io.javalin.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MainRoute
{
	public void configureRoutes(Javalin app) {
		app.get("/", ctx -> ctx.result("Hello World"));

		app.post("/upload", ctx -> {
			ctx.uploadedFiles("files").forEach(uploadedFile -> {
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try {
					ArrayList<Object> resultArray = JsonUtils.jsonFileToMapGson(reader);
					SharedData data = new SharedData(resultArray);
					ApplicationPriorityQueue applicationPriorityQueue = new ApplicationPriorityQueue(data.getSharedArray(), 0);
					// Do something with resultArray...
					System.out.println(data);
					System.out.println((applicationPriorityQueue.peek()));
				} catch (IOException e) {
					throw new RuntimeException("Failed to read file with GSON", e);
				}
			});
		});
	}


}
