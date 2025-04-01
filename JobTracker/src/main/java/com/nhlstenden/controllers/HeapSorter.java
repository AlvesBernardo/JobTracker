package com.nhlstenden.controllers;

import java.util.Comparator;
import java.util.List;

public class HeapSorter<T>
{
    private final Comparator<T> comparator;

    public HeapSorter(Comparator<T> comparator)
    {
        this.comparator = comparator;
    }

    public List<T> heapSort(List<T> list) {
        buildHeap(list);

        for (int i = list.size() - 1; i > 0; i--) {
            swap(list, 0, i); // Move the root (max or min) to the end
            heapifyDown(list, 0, i); // Restore heap property for the reduced heap
        }

        return list;
    }

    public void buildHeap(List<T> list) {
        for (int i = parent(list.size() - 1); i >= 0; i--) {
            heapifyDown(list, i, list.size());
        }
    }

    public void heapifyDown(List<T> list, int index, int size) {
        while (true) {
            int left = leftChild(index);
            int right = rightChild(index);
            int largest = index;

            if (left < size && comparator.compare(list.get(left), list.get(largest)) > 0) {
                largest = left;
            }
            if (right < size && comparator.compare(list.get(right), list.get(largest)) > 0) {
                largest = right;
            }
            if (largest == index) break;

            swap(list, index, largest);
            index = largest;
        }
    }

    public void heapifyUp(List<T> list, int index) {
        while (index > 0) {
            int parentIndex = parent(index);
            if (comparator.compare(list.get(index), list.get(parentIndex)) <= 0) break;
            swap(list, index, parentIndex);
            index = parentIndex;
        }
    }

    private void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }
}
