package com.nhlstenden.services;

import com.nhlstenden.middelware.MyHashTable;
import com.nhlstenden.middelware.MyArrayList;
import java.util.Map;

public class JobApplicationService {
    private MyHashTable<String, Map<String, Object>> jobApplications;

    public JobApplicationService() {
        jobApplications = new MyHashTable<>();
    }

    public void addApplications(MyArrayList<Object> applications) {
        for (Object obj : applications) {
            if (obj instanceof Map) {
                Map<String, Object> app = (Map<String, Object>) obj;
                Object idObj = app.get("id");
                if (idObj != null) {
                    String id = idObj instanceof Number ?
                            String.valueOf(((Number) idObj).intValue()) : idObj.toString();
                    jobApplications.put(id, app);
                }
            }
        }
    }

    public Map<String, Object> getApplication(String id) {
        System.out.println("Looking up application with id: " + id);
        return jobApplications.get(id);
    }
}
