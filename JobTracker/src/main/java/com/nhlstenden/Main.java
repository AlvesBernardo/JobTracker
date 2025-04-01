package com.nhlstenden;

import com.nhlstenden.configFiles.ConfigPlugin;
import com.nhlstenden.route.HashTableRoute;
import com.nhlstenden.route.MainRoute;
import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(ConfigPlugin::configure);
        app.start(8080);

        JobApplicationService<Map<String, Object>> jobAppService = new JobApplicationService<>();
        MainRoute<Map<String, Object>> mainRoute = new MainRoute<>(jobAppService);
        HashTableRoute<Map<String, Object>> hashTableRoute = new HashTableRoute<>(jobAppService);

        mainRoute.configureRoutes(app);
        hashTableRoute.configHashTableRoutes(app);
    }
}
