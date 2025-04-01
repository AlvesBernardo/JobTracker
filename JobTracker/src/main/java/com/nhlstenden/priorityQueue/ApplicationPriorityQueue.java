package com.nhlstenden.priorityQueue;

import com.nhlstenden.controllers.HeapSorter;
import com.nhlstenden.middelware.MyArrayList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ApplicationPriorityQueue<T>
{
    private List<T> heap;
    private final HeapSorter<T> heapSorter;

    public ApplicationPriorityQueue(List<T> applicationList, Comparator<T> comparator)
    {
        this.heap = new MyArrayList<>();
        this.heapSorter = new HeapSorter<>(comparator);
        heapSorter.buildHeap(applicationList);
        this.heap.addAll(applicationList);
    }

    public List<T> getHeap() {
        return heap;
    }

    public void add(T application)
    {
        heap.add(application);
        heapSorter.heapifyUp(heap,heap.size() - 1);
    }

    // retrieve and do not remove
    public T peek()
    {
        return heap.isEmpty() ? null : heap.get(0);
    }

    // retrieve and remove oldest entry
    public T poll()
    {
        if (heap.isEmpty()) return null;

        T root = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);

        if (! heap.isEmpty())
        {
            heap.set(0, lastElement);
            heapSorter.heapifyDown(heap,0, heap.size());
        }

        return root;
    }

    //update queue w/ the latest data
    public void updateQueue(List<T> applicationList)
    {
        heap.clear();
        heap.addAll(applicationList);
        heapSorter.buildHeap(heap);
    }

    // return and removes all elements in priority order
    public List<T> getSortedElements()
    {
        List<T> sortedList = new MyArrayList<>();
        sortedList.addAll(heap);
        return heapSorter.heapSort(sortedList);
    }

    // checks to see if queue is empty
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }
}
