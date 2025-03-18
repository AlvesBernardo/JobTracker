package com.nhlstenden.priorityQueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ApplicationPriorityQueue extends PriorityQueue
{
    private final PriorityQueue<Object[]> applicationPriorityQueue;
    private final int dateIndex;

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
        // Add elements individually
        applicationPriorityQueue.addAll(applicationList);
    }

    // retrieve and do not remove
    @Override
    public Object[] peek()
    {
        return applicationPriorityQueue.peek();
    }

    // retrieve and remove
    public Object[] poll()
    {
        if (!applicationPriorityQueue.isEmpty())
        {
            Object[] object = new Object[]{};
            object = applicationPriorityQueue.remove();
            return object;
        }
        return null;
    }

    // add a new array element to the queue
    public boolean offer(Object[] application)
    {
        if(applicationPriorityQueue != null)
        {
            applicationPriorityQueue.add(application);
            return true;
        }
        return false;

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

    @Override
    public boolean isEmpty()
    {
        return applicationPriorityQueue.isEmpty();
    }
}
