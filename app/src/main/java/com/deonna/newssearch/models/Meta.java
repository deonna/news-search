package com.deonna.newssearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("hits")
    @Expose
    public int hits;
    @SerializedName("time")
    @Expose
    public int time;
    @SerializedName("offset")
    @Expose
    public int offset;

}