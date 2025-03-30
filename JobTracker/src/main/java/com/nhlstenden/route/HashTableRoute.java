package com.nhlstenden.route;

import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;

public class HashTableRoute<T>
{
	private JobApplicationService<T> jobApplicationService = new JobApplicationService();

	public void configHashTableRoutes(Javalin app){
		app.get("/application/{id}", ctx -> {
			String id = ctx.pathParam("id");
			Object application = jobApplicationService.getApplication(id);
			if (application != null){
				ctx.json(application);
				ctx.status(200);
			}else {
				ctx.status(404).result("Application not found.");
			}
		});
	}
}
