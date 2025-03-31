package com.nhlstenden.services;

import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.middelware.MyHashTable;

import java.util.Map;

public class JobApplicationService<T>
{
    private MyHashTable<String, Map<String, T>> jobApplications;

    public JobApplicationService()
    {
        jobApplications = new MyHashTable<>();
    }

    public void addApplications(MyArrayList<T> applications)
    {
        for (Object obj : applications)
        {
            if (obj instanceof Map)
            {
                Map<String, T> app = (Map<String, T>) obj;
                Object idObj = app.get("id");
                if (idObj != null)
                {
                    String id = idObj instanceof Number ?
                            String.valueOf(((Number) idObj).intValue()) : idObj.toString();
                    jobApplications.put(id, app);
                }
            }
        }
    }

    public Map<String, T> getApplication(String id)
    {
        System.out.println("Looking up application with id: " + id);
        return jobApplications.get(id);
    }
}
