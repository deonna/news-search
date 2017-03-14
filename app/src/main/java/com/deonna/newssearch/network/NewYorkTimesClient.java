package com.deonna.newssearch.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NewYorkTimesClient {

    private static final String URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String API_KEY = "d97d63c8ff3d421e9ce6b451e9332a06";

    private AsyncHttpClient client;

    public NewYorkTimesClient() {

        client = new AsyncHttpClient();
    }

    public void getArticlesFromQuery(String query, JsonHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("page", 0);
        params.put("q", query);

        client.get(URL, params, handler);
    }

}
