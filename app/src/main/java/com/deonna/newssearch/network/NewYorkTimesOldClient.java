package com.deonna.newssearch.network;

import android.util.Log;

import com.deonna.newssearch.models.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class NewYorkTimesOldClient {

    private static final String TAG = NewYorkTimesOldClient.class.getSimpleName();

    private static final String URL = "https://api.nytimes.com/svc/search/v2/articlesearch" +
            ".json?api-key=%s";
    private static final String API_KEY = "d97d63c8ff3d421e9ce6b451e9332a06";


    //    private OkHttpClient client;
    private AsyncHttpClient client;

    public NewYorkTimesOldClient() {

//        client = new OkHttpClient();
        client = new AsyncHttpClient();
    }

//    public void getArticlesFromQuery(String query, JsonHttpResponseHandler handler) {
//
//        RequestParams params = new RequestParams();
//        params.put("api-key", API_KEY);
//        params.put("page", 0);
//        params.put("q", query);
//
//        client.get(URL, params, handler);
//    }
//
    public void getArticlesFromQuery(String query, JsonHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("page", 0);
        params.put("q", query);

        client.get(URL, params, handler);

        client.get(
                String.format(Locale.US, URL, API_KEY),
                new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        Log.d(TAG, "Failed to get response.");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        Gson gson = new GsonBuilder().create();
                        Article article = gson.fromJson(response, Article.class);
                    }
                });
    }

}
