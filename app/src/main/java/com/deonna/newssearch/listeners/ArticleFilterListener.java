package com.deonna.newssearch.listeners;


import java.util.Map;
import java.util.Properties;

public interface ArticleFilterListener {

    void onApplyFilters(String beginDate, String sortOrderParam, Map<String, Boolean> topics);
}
