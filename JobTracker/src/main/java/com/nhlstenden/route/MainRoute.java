package com.nhlstenden.route;


import com.google.common.reflect.TypeToken;
import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.BloomFilterMiddelware;
import com.nhlstenden.middelware.MyArrayList;
import io.javalin.Javalin;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.function.Function;

public class MainRoute<T>
{
	private SharedData<T> data;
	private SearchRoutes<T> searchRoutes;
	private BloomFilterMiddelware<T> bloomFilter;
	private boolean routesConfigured = false;
	public void configureRoutes(Javalin app)
	{
		app.post("/upload", ctx ->
		{
			ctx.uploadedFiles("files").forEach(uploadedFile ->
			{
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try
				{
					Type type = new TypeToken<MyArrayList<T>>()
					{
					}.getType(); // Define the Type
					MyArrayList<T> resultArray = JsonUtils.jsonFileToMapGson(reader, type);

					if (this.data == null){
						this.data = new SharedData<>(resultArray);
					} else {
						this.data.getSharedArray().addAll(resultArray);
					}
					ctx.status(200).result("Completed");
					if (!routesConfigured) {
						searchRoutes = new SearchRoutes<>(data);
						searchRoutes.configureRoutes(app);
						this.routesConfigured = true;
					}
					this.bloomFilter = new BloomFilterMiddelware<>(resultArray.size(), new Function[]{Object::hashCode});
					for (T element : resultArray)
					{
						this.bloomFilter.add(element);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					ctx.status(500).result("Error processing");
				}
			});
		});

	}
	public SharedData<T> getSharedData () {
		return data;

	}

}
