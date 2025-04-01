package com.nhlstenden.route;

import com.nhlstenden.controllers.SharedData;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class SortAlgo<T>
{
	private final SharedData<T> data;

	public SortAlgo(SharedData<T> data){
		this.data = data;
	}

	public void configureRoutes(Javalin app){
		app.get("/sort-data", ctx -> {
			if (data != null){
				long startTime = System.currentTimeMillis();
				data.sortByDate();
				long endTime = System.currentTimeMillis();
				long executionTime = endTime - startTime;

				Map<String, Object> response = new HashMap<>();
				response.put("sortedData", data.getSharedArray());
				response.put("executionTime", executionTime);
				ctx.json(response);
			}else {
				ctx.status(404).result("No data found");
			}
		});
	}

	public SharedData<T> getSharedData() {
		return data;
	}
}
