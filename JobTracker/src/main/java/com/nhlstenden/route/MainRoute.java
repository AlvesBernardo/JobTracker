package com.nhlstenden.route;


import com.nhlstenden.Main;
import com.nhlstenden.controllers.JsonUtils;
import io.javalin.Javalin;
import io.javalin.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

					// Do something with resultArray...
					System.out.println(resultArray);
				} catch (IOException e) {
					throw new RuntimeException("Failed to read file with GSON", e);
				}
			});
		});
	}


}
