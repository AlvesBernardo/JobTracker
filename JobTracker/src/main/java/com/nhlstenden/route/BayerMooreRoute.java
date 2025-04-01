package com.nhlstenden.route;

import com.nhlstenden.controllers.BayerMooreSearchController;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.MyArrayList;
import io.javalin.Javalin;

import java.util.Map;

public class BayerMooreRoute<T> {
    private final SharedData<T> sharedData;

    public BayerMooreRoute(SharedData<T> sharedData) {
        this.sharedData = sharedData;
    }

    public void configureRoutes(Javalin app) {
        app.get("/applications/bayer-search", ctx -> {
            String key = ctx.queryParam("key");
            String value = ctx.queryParam("value");

            if (key == null || value == null) {
                ctx.status(400).result("Missing 'key' or 'value' query parameter.");
                return;
            }

            BayerMooreSearchController<T> controller = new BayerMooreSearchController<>();
            long startTime = System.currentTimeMillis();
            MyArrayList<Map<String, T>> results = controller.searchByKey(sharedData.getSharedArray(), key, value);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            Map<String, Object> response = Map.of(
                    "results", results,
                    "executionTime", executionTime
            );
            if (results.isEmpty()) {
                ctx.status(404).result("No applications matched with pattern '" + value + "' in field '" + key + "'");
            } else {
                ctx.json(response);
                ctx.status(200);
            }
        });
    }
}
