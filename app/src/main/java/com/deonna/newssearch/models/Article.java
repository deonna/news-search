package com.deonna.newssearch.models;

import com.deonna.newssearch.models.articlesearch.Doc;
import com.deonna.newssearch.models.articlesearch.QueryResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Article {

    public static final String URL_PREFIX = "http://www.nytimes.com/%s";

    public String url;
    public String thumbnail;
    public String headline;
    public String section;
    public String snippet;
    public String publicationDate;
    public String formattedPublicationDate;

    public Article(Doc doc) {

        url = doc.webUrl;
        headline = doc.headline.main;
        section = doc.sectionName;
        snippet = doc.snippet;
        publicationDate = doc.pubDate;
        formattedPublicationDate = getFormattedPublicationDate(publicationDate);

        if (!doc.multimedia.isEmpty()) {
            //thumbnail = "http://www.nytimes.com/" + doc.multimedia.get(0).url;

            thumbnail = String.format(Locale.US, URL_PREFIX, doc.multimedia.get(0).url);
        }
    }

    public static String getFormattedPublicationDate(String dateToParse) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(dateToParse);
             return new SimpleDateFormat("M/dd/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

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
}
