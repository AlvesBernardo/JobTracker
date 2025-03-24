package com.nhlstenden.priorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPriorityQueueTest
{
    private ApplicationPriorityQueue queue;
    private List<Object[]> applicationList;

    @BeforeEach
    void setup()
    {
        applicationList = new ArrayList<>();

        applicationList.add(new Object[]{"App A", LocalDateTime.of(2023, 5, 10, 14, 0)});
        applicationList.add(new Object[]{"App B", LocalDateTime.of(2022, 8, 20, 9, 30)});
        applicationList.add(new Object[]{"App C", LocalDateTime.of(2024, 1, 15, 18, 45)});

        queue = new ApplicationPriorityQueue(applicationList, 1);
        queue.updateQueue(applicationList);
    }

}