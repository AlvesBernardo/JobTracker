package com.nhlstenden.priorityQueue;

import java.util.Comparator;

public class DateComparator<T> implements Comparator<T> {
    private final DateExtractor<T> dateExtractor;

    public DateComparator(DateExtractor<T> dateExtractor) {
        this.dateExtractor = dateExtractor;
    }

    @Override
    public int compare(T a, T b) {
        return dateExtractor.getDate(a).compareTo(dateExtractor.getDate(b));
    }
}