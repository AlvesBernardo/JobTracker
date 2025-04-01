package com.nhlstenden.route;

import com.google.gson.Gson;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.priorityQueue.ApplicationPriorityQueue;
import com.nhlstenden.priorityQueue.PriorityComparator;
import com.nhlstenden.utils.MyHashMap;
import io.javalin.Javalin;

import java.util.List;

public class PriorityQueueRoute<T>
{
    private final ApplicationPriorityQueue<T> priorityQueue;

    public PriorityQueueRoute(SharedData<T> sharedData)
    {
        List<T> initialElements = sharedData.getSharedArray();
        this.priorityQueue = new ApplicationPriorityQueue<>(initialElements, PriorityComparator.getDefaultComparator());
    }

    public void configureRoutes(Javalin app)
    {
        // Add a new element to the queue
        app.post("/priorityQueue/add", ctx ->
        {
            // Parse the raw JSON body into a generic element
            String rawBody = ctx.body();
            T element;

            try
            {
                // If T is a MyHashMap, parse it as such
                if (priorityQueue.getHeap().isEmpty() || priorityQueue.getHeap().get(0) instanceof MyHashMap)
                {
                    element = (T) new Gson().fromJson(rawBody, MyHashMap.class);
                }
                else
                {
                    // Otherwise, assume T is a simple type and parse it directly
                    element = (T) rawBody;
                }

                priorityQueue.add(element);
                ctx.status(200).result("Element added successfully!");
            } catch (Exception e)
            {
                ctx.status(400).result("Invalid element provided: " + e.getMessage());
            }

        });

        app.get("/priorityQueue/peek", ctx ->
        {
            T element = priorityQueue.peek();
            if (element != null)
            {
                ctx.json(element);
            }
            else
            {
                ctx.status(404).result("No elements in the queue.");
            }
        });

        // Get and remove the highest-priority element
        app.get("/priorityQueue/poll", ctx ->
        {
            T element = priorityQueue.poll();
            if (element != null)
            {
                ctx.json(element);
            }
            else
            {
                ctx.status(404).result("No elements in the queue.");
            }
        });

        // Get all elements sorted by priority
        app.get("/priorityQueue/sorted", ctx ->
        {
            List<T> sortedElements = priorityQueue.getSortedElements();
            ctx.json(sortedElements);
        });

        // Update the queue with a new list of elements
        app.put("/priorityQueue/update", ctx ->
        {
            List<T> elements = ctx.bodyAsClass(List.class);
            priorityQueue.updateQueue(elements);
            ctx.status(200).result("Queue updated successfully!");
        });

        // Check if the queue is empty
        app.get("/priorityQueue/isEmpty", ctx ->
        {
            boolean isEmpty = priorityQueue.isEmpty();
            ctx.json(isEmpty);
        });
    }
}
