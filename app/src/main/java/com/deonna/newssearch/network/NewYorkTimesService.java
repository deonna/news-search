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
    String KEY_FQ = "fq";

    @GET("articlesearch.json")
    Call<QueryResponse> getArticlesByPage(
            @Query(KEY_QUERY) String query,
            @Query(KEY_SORT) String sortOrder,
            @Query(KEY_BEGIN_DATE) String beginDate,
            @Query(KEY_FQ) String newsDeskFilter,
            @Query(KEY_PAGE) String page
    );

    @GET("articlesearch.json")
    Call<QueryResponse> getArticles(
      @Query(KEY_QUERY) String query,
      @Query(KEY_SORT) String sortOrder,
      @Query(KEY_BEGIN_DATE) String beginDate,
      @Query(KEY_FQ) String newsDeskFilter,
      @Query(KEY_PAGE) String page
    );
}
