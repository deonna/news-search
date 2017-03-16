package com.deonna.newssearch.network;

import com.deonna.newssearch.models.articlesearch.QueryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewYorkTimesService {

    public static final String KEY_QUERY = "q";
    public static final String KEY_BEGIN_DATE = "begin_date";
    public static final String KEY_END_DATE = "end_date";

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesFromQuery(@Query(KEY_QUERY) String query);

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesFromQueryBetweenDates(
            @Query(KEY_QUERY) String query,
            @Query(KEY_BEGIN_DATE) String startDate,
            @Query(KEY_END_DATE) String endDate
    );
}
