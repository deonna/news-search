package com.deonna.newssearch.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ArticlesFilter {

    private static final String FORMAT_PATTERN = "yyyyMMdd";

    public String beginDate = null;
    public String sortOrder = null;
    public Map<String, Boolean> topics;

    public ArticlesFilter() {

        topics = new HashMap<>();
    }

    public String setFormattedDate(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Date date = new Date(calendar.getTimeInMillis());

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN, Locale.US);

        beginDate = formatter.format(date);

        return beginDate;
    }

    public void addTopic(String topic, Boolean isChecked) {

        topics.put(topic.toLowerCase(), isChecked);
    }

    public String makeNewsDeskQuery() {

        String newsDeskFilter = null; //Format: fq=news_desk:("Education"%20"Health")

        for(Map.Entry<String, Boolean> entry : topics.entrySet()) {

            String topic = entry.getKey();
            Boolean isSelected = entry.getValue();

            if (isSelected) {

                if (newsDeskFilter == null) {
                    newsDeskFilter = String.format("news_desk:(\"%s\"", topic);
                } else {
                    newsDeskFilter = String.format("%s%%20\"%s\"", newsDeskFilter, topic);
                }
            }
        }

        if (newsDeskFilter != null) {
            newsDeskFilter = String.format("%s)", newsDeskFilter);
        }

        return newsDeskFilter;
    }
}
