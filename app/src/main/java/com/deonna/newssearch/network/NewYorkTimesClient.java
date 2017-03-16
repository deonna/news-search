package com.deonna.newssearch.network;

import android.util.Log;

import com.deonna.newssearch.models.QueryResponse;

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

    private static final String TAG = NewYorkTimesOldClient.class.getSimpleName();

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    private static final String API_KEY = "d97d63c8ff3d421e9ce6b451e9332a06";

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

    public void getArticlesFromQuery(String query) {

        Call<QueryResponse> call = service.getArticlesFromQuery("android");

        call.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, retrofit2.Response<QueryResponse> response) {
                int statusCode = response.code();
                QueryResponse queryResponse = response.body();
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {

                Log.d(TAG, "Failed to complete GET request");
            }
        });
    }

    public Interceptor getRequestInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();

                HttpUrl url = originalUrl
                        .newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build();

                Request request = originalRequest
                        .newBuilder()
                        .url(url)
                        .build();

                return chain.proceed(request);
            }
        };
    }
}
