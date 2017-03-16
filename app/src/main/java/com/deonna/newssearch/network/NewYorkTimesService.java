package com.deonna.newssearch.network;

import com.deonna.newssearch.models.articlesearch.QueryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewYorkTimesService {

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesFromQuery(@Query("q") String query);
}
