package com.nhlstenden.services;

import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.middelware.MyHashTable;

import java.util.Locale;
import java.util.Map;

public class JobApplicationService<T> {
    private MyHashTable<String, Map<String, T>> jobApplications;

    public JobApplicationService() {
        jobApplications = new MyHashTable<>();
    }

    public void addApplications(MyArrayList<T> applications) {
        for (T obj : applications) {
            if (obj instanceof Map) {
                Map<String, T> app = (Map<String, T>) obj;
                Object idObj = app.get("id");
                if (idObj != null) {
                    String id = idObj instanceof Number ?
                            String.valueOf(((Number) idObj).intValue()) : idObj.toString();
                    jobApplications.put(id, app);
                    System.out.println("Inserted application with ID: " + id);
                }
            }
        }
    }

    public Map<String, T> getApplication(String id) {
        return jobApplications.get(id);
    }

    public MyArrayList<Map<String, T>> searchApplications(String key, String value) {
        MyArrayList<Map<String, T>> results = new MyArrayList<>();
        String searchValue = value.toLowerCase(Locale.ROOT);

        for (int i = 0; i < jobApplications.size(); i++) {
            Map<String, T> application = jobApplications.getByIndex(i);
            if (application == null) continue;

            Object field = application.get(key);
            if (field != null) {
                String fieldValue = field.toString().toLowerCase(Locale.ROOT);
                System.out.println("Checking: [key=" + key + ", fieldValue=" + fieldValue + "] contains [" + searchValue + "]");

                if (fieldValue.contains(searchValue)) {
                    System.out.println("Match found in application ID: " + application.get("id"));
                    results.add(application);
                }
            } else {
                System.out.println("Key not found in application ID: " + application.get("id") + " for key: " + key);
            }
        }

        return results;
    }
}
