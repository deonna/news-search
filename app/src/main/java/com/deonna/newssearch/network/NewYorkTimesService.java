package com.deonna.newssearch.network;

import com.deonna.newssearch.models.articlesearch.QueryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewYorkTimesService {

    String KEY_QUERY = "q";
    String KEY_PAGE = "page";
    String KEY_BEGIN_DATE = "begin_date";
    String KEY_END_DATE = "end_date";
    String KEY_SORT = "sort";

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesFromQuery(@Query(KEY_QUERY) String query);

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesByPage(@Query(KEY_PAGE) String page, @Query(KEY_QUERY) String
            query);

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesFromQueryBetweenDates(
            @Query(KEY_QUERY) String query,
            @Query(KEY_BEGIN_DATE) String startDate,
            @Query(KEY_END_DATE) String endDate
    );

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesSortedOldestToNewest(
            @Query(KEY_QUERY) String query,
            @Query(KEY_SORT) String sortOrder
    );

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesSortedNewestToOldest(
            @Query(KEY_QUERY) String query,
            @Query(KEY_SORT) String sortOrder
    );
}
