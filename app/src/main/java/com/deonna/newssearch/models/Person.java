package com.deonna.newssearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("firstname")
    @Expose
    public String firstName;

    @SerializedName("middlename")
    @Expose
    public String middleName;

    @SerializedName("lastname")
    @Expose
    public String lastName;

    @SerializedName("rank")
    @Expose
    public int rank;

    @SerializedName("role")
    @Expose
    public String role;

    @SerializedName("organization")
    @Expose
    public String organization;
}
