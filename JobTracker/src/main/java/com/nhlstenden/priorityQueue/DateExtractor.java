package com.nhlstenden.priorityQueue;

import java.time.LocalDate;

public interface DateExtractor<T>
{
    LocalDate getDate(T item);
}
