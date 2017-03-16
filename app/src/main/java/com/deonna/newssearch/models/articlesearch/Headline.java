package com.deonna.newssearch.models.articlesearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("kicker")
    @Expose
    public String kicker;

}