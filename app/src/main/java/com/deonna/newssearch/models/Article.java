package com.deonna.newssearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.deonna.newssearch.models.articlesearch.Doc;
import com.deonna.newssearch.models.articlesearch.QueryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Article implements Parcelable {

    public static final String URL_PREFIX = "http://www.nytimes.com/%s";

    public String url;
    public String thumbnail;
    public String headline;
    public String section;
    public String snippet;
    public String publicationDate;

    public Article(Doc doc) {

        url = doc.webUrl;
        headline = doc.headline.main;
        section = doc.sectionName;
        snippet = doc.snippet;
        publicationDate = doc.pubDate;

        if (!doc.multimedia.isEmpty()) {
            //thumbnail = "http://www.nytimes.com/" + doc.multimedia.get(0).url;

            thumbnail = String.format(Locale.US, URL_PREFIX, doc.multimedia.get(0).url);
        }
    }

    protected Article(Parcel in) {
        url = in.readString();
        thumbnail = in.readString();
        headline = in.readString();
        section = in.readString();
        snippet = in.readString();
        publicationDate = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public static List<Article> fromQueryResponse(QueryResponse queryResponse)  {

        List<Article> articles = new ArrayList<>();

        List<Doc> docs = queryResponse.response.docs;

        if (!docs.isEmpty()) {

            for (int i = 0; i < docs.size(); i++) {

                Doc doc = docs.get(i);
                articles.add(new Article(doc));
            }
        }

        return articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(thumbnail);
        dest.writeString(headline);
        dest.writeString(section);
        dest.writeString(snippet);
        dest.writeString(publicationDate);
    }
}
