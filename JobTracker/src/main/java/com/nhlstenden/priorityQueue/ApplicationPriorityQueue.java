package com.nhlstenden.priorityQueue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ApplicationPriorityQueue
{
    private List<Object[]> heap;
    private final int dateIndex;

    public ApplicationPriorityQueue(List<Object[]> applicationList, int dateIndex)
    {
        this.dateIndex = dateIndex;
        this.heap = new ArrayList<>();
        buildHeap(applicationList);
    }

    // Build heap using bottom-up heapify, O(n) time
    private void buildHeap(List<Object[]> list)
    {
        heap.clear();
        heap.addAll(list);
        for (int i = parent(heap.size() - 1); i >= 0; i--)
        {
            heapifyDown(i);
        }
    }

    public boolean add(Object[] application)
    {
        heap.add(application);
        heapifyUp(heap.size() - 1);
    }

    // retrieve and do not remove
    public Object[] peek()
    {
        return heap.isEmpty() ? null : heap.get(0);
    }

    // retrieve and remove oldest entry
    public Object[] poll()
    {
        if (heap.isEmpty()) return null;

        Object[] root = heap.get(0);
        Object[] lastElement = heap.remove(heap.size() - 1);

        if(!heap.isEmpty())
        {
            heap.set(0, lastElement);
            heapifyDown(0);
        }

        return root;
    }

    //update queue w/ the latest data
    public void updateQueue(List<Object[]> applicationList)
    {
        buildHeap(applicationList);
    }

    // return and removes all elements in priority order
    public List<Object[]> getSortedElements()
    {
        List<Object[]> sortedList = new ArrayList<>();
        while (!heap.isEmpty())
        {
            sortedList.add(poll()); // Poll to maintain priority order
        }
        return sortedList;
    }

    // checks to see if queue is empty
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    // Heapify up: maintain heap property when adding an element
    private void heapifyUp(int index)
    {
        while (index > 0)
        {
            int parentIndex = parent(index);
            if (compare(heap.get(index), heap.get(parentIndex)) >= 0) break;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Heapify down: maintain heap property when removing the root
    private void heapifyDown(int index)
    {
        while(true)
        {
            int left = leftChild(index);
            int right = rightChild(index);
            int smallest = index;

            if (left < heap.size() & compare(heap.get(left), heap.get(smallest)) < 0)
            {
                smallest = left;
            }
            if (right < heap.size() & compare(heap.get(right), heap.get(smallest)) < 0)
            {
                smallest = right;
            }
            if (smallest == index) break;

            swap(index, smallest);
            index = smallest;
        }
    }

    //Comparator for two objects based on LocalDate
    private int compare(Object a, Object b)
    {
        LocalDate date1 = (LocalDate) a[dateIndex];
        LocalDate date2 = (LocalDate) b[dateIndex];
        return date1.compareTo(date2);
    }

    //Swapping two elements in the heap
    private void swap(int i, int j)
    {
        Object[] temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // helper methods for heap indexing
    private int parent(int i)
    {
        return (i-1)/2;
    }

    private int leftChild(int i)
    {
        return 2 * i + 1;
    }

    private int rightChild(int i)
    {
        return 2 * i +2;
    }
}
