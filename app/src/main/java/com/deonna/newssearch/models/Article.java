package com.deonna.newssearch.models;

import com.deonna.newssearch.models.articlesearch.Doc;
import com.deonna.newssearch.models.articlesearch.Multimedium;
import com.deonna.newssearch.models.articlesearch.QueryResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Article {

    public static final String URL_PREFIX = "http://www.nytimes.com/%s";
    public static final String MAX_WIDTH = "600";

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

            int greatestWidth = Integer.MIN_VALUE;
            String bestImagePath = doc.multimedia.get(0).url;

            for (Multimedium m : doc.multimedia) {

                 if (m.width > greatestWidth) {
                     bestImagePath = m.url;
                 }
            }

            thumbnail = String.format(Locale.US, URL_PREFIX, bestImagePath);
        }
    }

    public static String getFormattedPublicationDate(String dateToParse) {

        try {

            if (dateToParse != null) {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(dateToParse);
                return new SimpleDateFormat("M/dd/yyyy").format(date);
            } else {
                return "";
            }
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
