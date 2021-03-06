package com.deonna.newssearch.network;

import com.deonna.newssearch.BuildConfig;
import com.deonna.newssearch.models.articlesearch.QueryResponse;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewYorkTimesClient {

    private static final String TAG = NewYorkTimesClient.class.getSimpleName();

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    private static final String KEY_API = "api-key";

    private OkHttpClient client;
    private Retrofit retrofit;
    private NewYorkTimesService service;

    public NewYorkTimesClient() {

        client = new OkHttpClient.Builder()
                .addInterceptor(getRequestInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(NewYorkTimesService.class);
    }

    public void getArticlesByPage(
            String query,
            String sortOrder,
            String beginDate,
            String newsDeskFilter,
            String page,
            Callback<QueryResponse> callback) {

        Call<QueryResponse> call = service.getArticlesByPage(query, sortOrder, beginDate,
                newsDeskFilter, page);

        call.enqueue(callback);
    }

    public Interceptor getRequestInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();

                HttpUrl url = originalUrl
                        .newBuilder()
                        .addQueryParameter(KEY_API, BuildConfig.NYT_API_KEY)
                        .build();

                Request request = originalRequest
                        .newBuilder()
                        .url(url)
                        .build();

                return chain.proceed(request);
            }
        };
    }

    public void getArticles(
            String query,
            String sortOrder,
            String beginDate,
            String newsDeskFilter,
            String page,
            Callback<QueryResponse> callback
    ) {

        Call<QueryResponse> call = service.getArticles(query, sortOrder, beginDate, newsDeskFilter, page);

        call.enqueue(callback);
    }
}
