package com.deonna.newssearch.models;

import com.deonna.newssearch.models.articlesearch.Doc;
import com.deonna.newssearch.models.articlesearch.QueryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Article {

    public static final String URL_PREFIX = "http://www.nytimes.com/%s";

    public String url;
    public String headline;
    public String thumbnail;

    public Article(Doc doc) {

        url = doc.webUrl;
        headline = doc.headline.main;

        if (!doc.multimedia.isEmpty()) {
            thumbnail = String.format(Locale.US, URL_PREFIX, doc.multimedia.get(0).url);
        }
    }

    public static List<Article> addAll(QueryResponse queryResponse)  {

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
}
