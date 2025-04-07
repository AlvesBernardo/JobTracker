package com.nhlstenden.priorityQueue;

import com.nhlstenden.utils.MyHashMap;

import java.util.Comparator;
import java.util.Date;

public class DateComparator
{
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> getDefaultComparator()
    {
        return (o1, o2) ->
        {
            // Handle MyHashMap comparison by "date_applied"
            if (o1 instanceof MyHashMap && o2 instanceof MyHashMap)
            {
                MyHashMap<String, ?> map1 = (MyHashMap<String, ?>) o1;
                MyHashMap<String, ?> map2 = (MyHashMap<String, ?>) o2;

                if (map1.containsKey("date_applied") && map2.containsKey("date_applied"))
                {
                    Date date1 = new Date(map1.get("date_applied").toString());
                    Date date2 = new Date(map2.get("date_applied").toString());
                    return date1.compareTo(date2); // Sort by date
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
