package com.deonna.newssearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doc {

    @SerializedName("web_url")
    @Expose
    public String webUrl;
    @SerializedName("snippet")
    @Expose
    public String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    public String leadParagraph;
    @SerializedName("abstract")
    @Expose
    public String _abstract;
    @SerializedName("print_page")
    @Expose
    public String printPage;
    @SerializedName("blog")
    @Expose
    public List<String> blog = null;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("headline")
    @Expose
    public Headline headline;
    @SerializedName("keywords")
    @Expose
    public List<Keyword> keywords;
    @SerializedName("pub_date")
    @Expose
    public String pubDate;
    @SerializedName("document_type")
    @Expose
    public String documentType;
    @SerializedName("news_desK")
    @Expose
    public String newsDesK;
    @SerializedName("section_name")
    @Expose
    public String sectionName;
    @SerializedName("subsection_name")
    @Expose
    public String subsectionName;
    @SerializedName("byline")
    @Expose
    public Byline byline;
    @SerializedName("type_of_material")
    @Expose
    public String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("word_count")
    @Expose
    public String wordCount;
    @SerializedName("slideshow_credits")
    @Expose
    public String slideshowCredits;
    @SerializedName("multimedia")
    @Expose
    public List<Multimedium> multimedia = null;

}