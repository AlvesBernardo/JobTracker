package com.nhlstenden.route;

import com.google.gson.Gson;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.BinaryMiddelWare;
import com.nhlstenden.middelware.BloomFilterMiddelware;
import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.utils.MyBetterMap;
import com.nhlstenden.utils.MyHashMap;
import io.javalin.Javalin;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class SearchRoutes<T>
{
    private final BinaryMiddelWare<T> binaryMiddelWare = new BinaryMiddelWare<>();
    private final SharedData<T> data;
    private final BloomFilterMiddelware<T> bloomFilter;

    // Constructor to initialize SharedData and BloomFilter
    public SearchRoutes(SharedData<T> data)
    {
        this.data = data;
        this.bloomFilter = new BloomFilterMiddelware<>(data.getSharedArray().size(), new Function[]{Object::hashCode});
    }

    // Method to configure routes
    public void configureRoutes(Javalin app)
    {
        // Binary Search Route
        app.post("/binarySearch", ctx ->
        {
            MyHashMap<String, String> requestBody = new Gson().fromJson(ctx.body(), MyHashMap.class);
            String key = requestBody.get("key");
            String value = requestBody.get("value");

            if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty())
            {
                ctx.status(400).result("Invalid search request. Both key and value are required.");
                return;
            }
            long startTime = System.currentTimeMillis();
            Object foundJob = binaryMiddelWare.doBinarySearch(data, requestBody);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            MyBetterMap<String, Object> response = new MyBetterMap<>();
            response.put("executionTime", executionTime);
            if (foundJob != null)
            {
                ctx.json(foundJob);
                System.out.println("Found job: " + foundJob);
            } else
            {
                ctx.status(404).result("Job not found.");
                System.out.println("Job not found.");
            }
        });


        // Bloom Filter Check Route
        app.post("/bloom/check-date", ctx ->
        {
            // Parse the JSON object from the request body
            MyHashMap<String, Object> requestBody = new Gson().fromJson(ctx.body(), MyHashMap.class);

            // Extract the date string from the JSON object
            String dateString = (String) requestBody.get("date_applied");

            // Convert the date string to a Date object
            Date date = new Gson().fromJson("\"" + dateString + "\"", Date.class);

            long startTime = System.currentTimeMillis();
            // Check if the Bloom filter might contain the date
            boolean mightContain = this.bloomFilter.mightContain((T) date);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            Map<String, Object> response = new MyBetterMap<>();
            response.put("executionTime", executionTime);
            if (mightContain)
            {
                // Create a list to store elements with the matching date
                MyArrayList<T> resultArray = new MyArrayList<>();

                // Iterate through the shared data array
                for (T element : data.getSharedArray())
                {
                    // Check if the element is a Map
                    if (element instanceof MyHashMap)
                    {
                        // Cast the element to a MyHashMap
                        MyHashMap<String, Object> map = (MyHashMap<String, Object>) element;

                        // Extract the date from the map and convert it to a Date object
                        Date elementDate = new Gson().fromJson("\"" + map.get("date_applied").toString() + "\"", Date.class);

                        // Check if the element's date matches the requested date
                        if (elementDate.equals(date))
                        {
                            // Add the element to the result array
                            resultArray.add(element);
                        }
                    }
                }

                // Return the result array as JSON
                ctx.json(resultArray);
            } else
            {
                // Return a 404 status if the date is not found in the Bloom filter
                ctx.status(404).result("Date not found in Bloom filter.");
            }
        });
    }

    public SharedData<T> getSharedData()
    {
        return data;
    }
}
