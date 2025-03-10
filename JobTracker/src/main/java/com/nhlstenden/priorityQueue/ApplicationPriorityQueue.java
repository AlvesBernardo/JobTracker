package com.nhlstenden.priorityQueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ApplicationPriorityQueue
{
    private final PriorityQueue<Object[]> applicationPriorityQueue;
    private final int dateIndex;

    // [ToDo] There is the chance for two objects to have the same time
    // [ToDo] When there is a new application, it needs to get added to the priority queue
    // [ToDo] Will the priority queue update if the list from which it is created from updates

    public ApplicationPriorityQueue(List<Object[]> applicationList, int dateIndex)
    {
        this.dateIndex = dateIndex;

        Comparator<Object[]> comparator = (o1, o2) ->
        {
            LocalDateTime date1 = (LocalDateTime) o1[dateIndex];
            LocalDateTime date2 = (LocalDateTime) o2[dateIndex];
            return date1.compareTo(date2); // Ascending order (oldest first)
        };

        this.applicationPriorityQueue = new PriorityQueue<>(comparator);
    }

    //make a trigger here
    public void updateQueue(List<Object[]> applicationList)
    {
        applicationPriorityQueue.clear();
        for (Object[] array : applicationList)
        {
            applicationPriorityQueue.add(array); // Add elements individually
        }
    }

    // retrieve and do not remove
    public Object[] peek()
    {
        return applicationPriorityQueue.peek();
    }

    // retrieve and remove
    public Object[] poll()
    {
        return applicationPriorityQueue.poll();
    }

    // add a new array element to the queue
    public void add(Object[] application)
    {
        applicationPriorityQueue.add(application);
    }

    // return and removes all elements in priority order
    public List<Object[]> getSortedElements()
    {
        List<Object[]> sortedList = new ArrayList<>();
        while (! applicationPriorityQueue.isEmpty())
        {
            sortedList.add(applicationPriorityQueue.poll()); // Poll to maintain priority order
        }
        return sortedList;
    }

    public boolean isEmpty()
    {
        return applicationPriorityQueue.isEmpty();
    }
}
