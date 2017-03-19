package com.deonna.newssearch.listeners;

import android.support.v7.widget.SearchView;

import com.deonna.newssearch.utilities.ArticleLoader;

public class ArticleQueryHandler implements SearchView.OnQueryTextListener {

    private ArticleLoader articleLoader;
    private SearchView searchView;

    public ArticleQueryHandler(SearchView searchView, ArticleLoader articleLoader) {

        this.searchView = searchView;
        this.articleLoader = articleLoader;

        setArticleQueryListener();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        articleLoader.loadArticles(query);
        searchView.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void setArticleQueryListener() {

        searchView.setOnQueryTextListener(this);
    }
}
