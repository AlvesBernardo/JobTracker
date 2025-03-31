package com.nhlstenden.route;

import com.nhlstenden.controllers.SharedData;
import io.javalin.Javalin;

public class SortAlgo<T>
{
	private final SharedData<T> data;

	public SortAlgo(SharedData<T> data){
		this.data = data;
	}

	public void configureRoutes(Javalin app){
		app.get("/sort-data", ctx -> {
			if (data != null){
				data.sortByDate();
				ctx.json(data.getSharedArray());
			}else {
				ctx.status(404).result("No data found");
			}
		});
	}

	public SharedData<T> getSharedData() {
		return data;
	}
}
