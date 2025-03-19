package com.nhlstenden.route;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.BinaryMiddelWare;
import com.nhlstenden.middelware.BloomFilterMiddelware;
import io.javalin.Javalin;

import java.util.Map;
import java.util.function.Function;

public class SearchRoutes<T>
{
	private final BinaryMiddelWare<T> binaryMiddelWare = new BinaryMiddelWare<>();
	private final SharedData<T> data;
	private final BloomFilterMiddelware<T> bloomFilter;

	// Constructor to initialize SharedData and BloomFilter
	public SearchRoutes(SharedData<T> data) {
		this.data = data;
		this.bloomFilter = new BloomFilterMiddelware<>(data.getSharedArray().size(), new Function[]{Object::hashCode});
		for (T element : data.getSharedArray())
		{
			this.bloomFilter.add(element);
		}
	}

	// Method to configure routes
	public void configureRoutes(Javalin app) {
		// Binary Search Route
		app.post("/binarySearch", ctx -> {
			Map<String, String> requestBody = new Gson().fromJson(ctx.body(), Map.class);
			String key = requestBody.get("key");
			String value = requestBody.get("value");

			if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty()) {
				ctx.status(400).result("Invalid search request. Both key and value are required.");
				return;
			}

			Object foundJob = binaryMiddelWare.doBinarySearch(data, requestBody);

			if (foundJob != null) {
				ctx.json(foundJob);
				System.out.println("Found job: " + foundJob);
			} else {
				ctx.status(404).result("Job not found.");
				System.out.println("Job not found.");
			}
		});

		// Bloom Filter Add Route
		app.post("/bloom/add", ctx -> {
			T element = new Gson().fromJson(ctx.body(), new TypeToken<T>() {}.getType());
			this.bloomFilter.add(element);
			ctx.status(200).result("Element added to Bloom filter.");
		});

		// Bloom Filter Check Route
		app.post("/bloom/check", ctx -> {
			T element = new Gson().fromJson(ctx.body(), new TypeToken<T>() {}.getType());
			boolean mightContain = this.bloomFilter.mightContain(element);
			ctx.status(200).result("Might contain element: " + mightContain);
		});
	}
}