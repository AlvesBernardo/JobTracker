package com.nhlstenden.route;

import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;
import java.io.InputStreamReader;

public class MainRoute {

	private JobApplicationService jobApplicationService = new JobApplicationService();

	public void configureRoutes(Javalin app) {
		app.get("/", ctx -> ctx.result("Hello World"));

		app.post("/upload", ctx -> {
			ctx.uploadedFiles("files").forEach(uploadedFile -> {
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try {
					MyArrayList<Object> resultArray = JsonUtils.jsonFileToMapGson(reader);
					SharedData<Object> data = new SharedData<>(resultArray);
					System.out.println("Uploaded Data: " + data.getString());

					jobApplicationService.addApplications(resultArray);

					ctx.result("File uploaded and job applications stored successfully.");
				} catch (Exception e) {
					e.printStackTrace();
					ctx.status(500).result("Error processing file.");
				}
			});
		});

		app.get("/application/{id}", ctx -> {
			String id = ctx.pathParam("id");
			Object application = jobApplicationService.getApplication(id);
			if (application != null) {
				ctx.json(application);
			} else {
				ctx.status(404).result("Application not found.");
			}
		});
	}
}
