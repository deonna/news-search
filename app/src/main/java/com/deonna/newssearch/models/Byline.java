package com.deonna.newssearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Byline {

    @SerializedName("organization")
    @Expose
    public String organization;
    @SerializedName("original")
    @Expose
    public String original;
    @SerializedName("person")
    @Expose
    public List<Person> person = null;

}