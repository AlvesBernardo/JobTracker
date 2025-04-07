package com.nhlstenden.priorityQueue;

import com.nhlstenden.utils.MyHashMap;

import java.util.Comparator;

public class PriorityComparator
{
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> getDefaultComparator()
    {
        return (o1, o2) ->
        {
            // Handle MyHashMap comparison by "priority"
            if (o1 instanceof MyHashMap && o2 instanceof MyHashMap)
            {
                MyHashMap<String, ?> map1 = (MyHashMap<String, ?>) o1;
                MyHashMap<String, ?> map2 = (MyHashMap<String, ?>) o2;

                if (map1.containsKey("priority") && map2.containsKey("priority"))
                {
                    Integer priority1 = Integer.parseInt(map1.get("priority").toString());
                    Integer priority2 = Integer.parseInt(map2.get("priority").toString());
                    return priority1.compareTo(priority2); // Compare by priority
                }
            }

            // Fallback to natural ordering if possible
            if (o1 instanceof Comparable && o2 instanceof Comparable)
            {
                return ((Comparable<T>) o1).compareTo(o2);
            }

            // Default to equality if no comparison is possible
            return 0;
        };
    }
}
