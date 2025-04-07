package com.nhlstenden.route;

import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;

import java.util.Map;

public class HashTableRoute<T>
{
    private final JobApplicationService<T> jobApplicationService;

    public HashTableRoute(JobApplicationService<T> jobApplicationService)
    {
        this.jobApplicationService = jobApplicationService;
    }

    public void configHashTableRoutes(Javalin app)
    {
        app.get("/application/{id}", ctx ->
        {
            String id = ctx.pathParam("id");
            Map<String, T> application = jobApplicationService.getApplication(id);
            if (application != null)
            {
                ctx.json(application);
                ctx.status(200);
            } else
            {
                ctx.status(404).result("Application not found.");
            }
        });

        app.get("/applications/search", ctx ->
        {
            String key = ctx.queryParam("key");
            String value = ctx.queryParam("value");

            if (key == null || value == null)
            {
                ctx.status(400).result("Missing key or value for search.");
                return;
            }
            long startTime = System.currentTimeMillis();
            MyArrayList<Map<String, T>> results = jobApplicationService.searchApplications(key, value);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            Map<String, Object> response = Map.of(
                    "results", results,
                    "executionTime", executionTime
            );
            if (results.isEmpty())
            {
                ctx.status(404).result("No applications found for key '" + key + "' with value like '" + value + "'.");
            } else
            {
                ctx.json(response);
                ctx.status(200);
            }
        });
    }
}
