package com.deonna.newssearch.listeners;

import android.support.v7.widget.SearchView;

import com.deonna.newssearch.utilities.EndlessScrollHandler;

public class ArticleQueryListener implements SearchView.OnQueryTextListener {

    private EndlessScrollHandler endlessScrollHandler;
    private SearchView searchView;

    public ArticleQueryListener(SearchView searchView, EndlessScrollHandler endlessScrollHandler) {

        this.searchView = searchView;
        this.endlessScrollHandler = endlessScrollHandler;

        setArticleQueryListener();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        endlessScrollHandler.currentQuery = query;

        endlessScrollHandler.loadArticleByQuery(query);
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
