package com.deonna.newssearch.models.articlesearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryResponse {

    @SerializedName("response")
    @Expose
    public Response response;

}