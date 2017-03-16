package com.deonna.newssearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    @Expose
    public List<Doc> docs = null;
    @SerializedName("meta")
    @Expose
    public Meta meta;

}