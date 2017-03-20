package com.deonna.newssearch.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.adapters.ArticlesAdapter;
import com.deonna.newssearch.fragments.FilterFragment;
import com.deonna.newssearch.listeners.ArticleQueryHandler;
import com.deonna.newssearch.listeners.ArticlesFilterListener;
import com.deonna.newssearch.listeners.EmptyViewListener;
import com.deonna.newssearch.listeners.ProgressBarListener;
import com.deonna.newssearch.listeners.RefreshListener;
import com.deonna.newssearch.listeners.ScrollToTopListener;
import com.deonna.newssearch.listeners.SnackbarListener;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.models.ArticlesFilter;
import com.deonna.newssearch.utilities.ArticleLoader;
import com.deonna.newssearch.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements ArticlesFilterListener,
        ProgressBarListener, ScrollToTopListener, SnackbarListener, RefreshListener,
        EmptyViewListener {

    private static final int NUM_COLUMNS = 2;
    private static final String QUERY_HINT = "Enter a search query...";

    @BindView(R.id.dlArticles) DrawerLayout dlArticles;

    @BindView(R.id.pbArticlesLoading) ProgressBar pbArticlesLoading;

    @BindView(R.id.tbArticles) Toolbar tbArticles;

    @BindView(R.id.srlArticles) SwipeRefreshLayout srlArticles;

    @BindView(R.id.rvArticles) RecyclerView rvArticles;

    @BindView(R.id.svArticle) SearchView svArticle;

    @BindView(R.id.ivFilter) ImageView ivFilter;

    @BindView(R.id.tvNoData) TextView tvNoData;

    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    private ArticlesFilter articlesFilter;
    private ArticleLoader articleLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(tbArticles);

        initializeArticleList();

        svArticle.setQueryHint(QUERY_HINT);

        ArticleQueryHandler articleQueryHandler = new ArticleQueryHandler(svArticle, articleLoader);

        ivFilter.setOnClickListener((view) -> {
            openFilterDialog();
        });

        monitorConnectivity();
    }

    public void monitorConnectivity() {

        BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (!Utils.isNetworkAvailable(SearchActivity.this)) {
                    showNetworkDisconnectedView();
                }
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
    }

    private void initializeArticleList() {

        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(this, articles, SearchActivity.this);

        articlesFilter = new ArticlesFilter();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, StaggeredGridLayoutManager
                .VERTICAL);

        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(layoutManager);

        articleLoader = new ArticleLoader(articles, articlesAdapter, layoutManager, SearchActivity.this);

        rvArticles.addOnScrollListener(articleLoader.scrollListener);

        srlArticles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                articleLoader.refreshArticles();
            }
        });

        articleLoader.loadArticles(null);
    }

    public void openFilterDialog() {

        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance(articlesFilter);
        filterFragment.show(fm, FilterFragment.NAME);
    }

    @Override
    public void onApplyFilters(ArticlesFilter articlesFilter) {

        String newsDeskFilter = articlesFilter.makeNewsDeskQuery();
        String page = null;

        if (newsDeskFilter != null) {
            clearQuery();
        }

        articleLoader.loadArticles(
                articleLoader.query,
                articlesFilter.sortOrderParam,
                articlesFilter.beginDateFormatted,
                newsDeskFilter,
                page
        );
    }

    private void clearQuery() {

        articleLoader.query = null;
        svArticle.setQuery(null, true);
    }

    @Override
    public void showProgressBar() {

        pbArticlesLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {

        pbArticlesLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void scrollToTop() {

        rvArticles.smoothScrollToPosition(0);
    }

    @Override
    public void showSnackbar() {

        Snackbar.make(dlArticles, R.string.loading, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void finishRefreshing() {

        srlArticles.setRefreshing(false);
    }

    @Override
    public void showEmptyView() {

        rvArticles.setVisibility(View.GONE);

        String noResults = getResources().getString(R.string.no_article_information);
        tvNoData.setText(noResults);

        tvNoData.setVisibility(View.VISIBLE);
        pbArticlesLoading.setVisibility(View.GONE);
    }

    @Override
    public void showNormalView() {

        rvArticles.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkDisconnectedView() {

        rvArticles.setVisibility(View.GONE);

        String networkDisconnected = getResources().getString(R.string.network_disconnected);
        tvNoData.setText(networkDisconnected);

        tvNoData.setVisibility(View.VISIBLE);
        pbArticlesLoading.setVisibility(View.GONE);
    }
}
