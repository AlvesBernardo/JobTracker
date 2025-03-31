package com.nhlstenden.priorityQueue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MapDateExtractor implements DateExtractor<Map<String, Object>> {
    @Override
    public LocalDate getDate(Map<String, Object> application) {
        String dateStr = (String) application.get("submissionDate");
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }
}
