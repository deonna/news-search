package com.deonna.newssearch.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.deonna.newssearch.adapters.ArticlesAdapter;
import com.deonna.newssearch.listeners.EmptyViewListener;
import com.deonna.newssearch.listeners.ProgressBarListener;
import com.deonna.newssearch.listeners.RefreshListener;
import com.deonna.newssearch.listeners.ScrollToTopListener;
import com.deonna.newssearch.listeners.SnackbarListener;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.models.articlesearch.QueryResponse;
import com.deonna.newssearch.network.NewYorkTimesClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleLoader {

    private static final String TAG = ArticleLoader.class.getSimpleName();
    private static final String ERROR = "Error loading articles!";

    public static final String KEY_NEWEST = "newest";
    public static final String KEY_OLDEST = "oldest";

    public EndlessRecyclerViewScrollListener scrollListener;

    private int currentPage = 0;

    private NewYorkTimesClient client;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;
    private ProgressBarListener progressBarListener;
    private ScrollToTopListener scrollToTopListener;
    private SnackbarListener snackbarListener;
    private RefreshListener refreshListener;
    private EmptyViewListener emptyViewListener;

    private Context context;

    //Current filters
    public String query = null;
    private String sortOrder = null;
    private String beginDate = null;
    private String newsDeskFilter = null;
    private String page = null;

    private boolean isRefreshing = false;

    public ArticleLoader(List<Article> articles,
                         ArticlesAdapter articlesAdapter,
                         StaggeredGridLayoutManager layoutManager,
                         Context context
    ) {

        client = new NewYorkTimesClient();
        this.context = context;
        this.articles = articles;
        this.articlesAdapter = articlesAdapter;

        scrollListener = initializeEndlessScrollListener(layoutManager);
        this.progressBarListener =  (ProgressBarListener) context;
        this.scrollToTopListener = (ScrollToTopListener) context;
        this.snackbarListener = (SnackbarListener) context;
        this.refreshListener = (RefreshListener) context;
        this.emptyViewListener = (EmptyViewListener) context;
    }

    private EndlessRecyclerViewScrollListener initializeEndlessScrollListener(StaggeredGridLayoutManager layoutManager) {

        return new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, final RecyclerView view) {

                loadEndlessScrollArticles(currentPage);
            }
        };
    }

    private void loadEndlessScrollArticles(int page) {

       if (newsDeskFilter != null) {
           query = null;
       }

       if (!isRefreshing) {
           snackbarListener.showSnackbar();
       }

       client.getArticlesByPage(
               query,
               sortOrder,
               beginDate,
               newsDeskFilter,
               Integer.valueOf(page).toString(),
               new Callback<QueryResponse>() {
                @Override
                public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {

                    if (response.isSuccessful()) {

                        QueryResponse queryResponse = response.body();
                        List<Article> moreArticles = Article.fromQueryResponse(queryResponse);

                        articles.addAll(moreArticles);
                        notifyArticlesChanged(moreArticles.size());
                    }
                }

                @Override
                public void onFailure(Call<QueryResponse> call, Throwable t) {

                    if (Utils.isNetworkAvailable(context)) {
                        emptyViewListener.showEmptyView();
                    } else {
                        emptyViewListener.showNetworkDisconnectedView();
                    }

                    Log.e(TAG, ERROR);
                    t.printStackTrace();
                }
        });

        currentPage = page + 1;
        scrollListener.resetState();
    }

    private void notifyArticlesChanged(int newArraySize) {

        int oldCount = articlesAdapter.getItemCount();
        articlesAdapter.notifyItemRangeInserted(oldCount, newArraySize);

        articlesAdapter.notifyAdapterDataSetChanged();
    }

    private void resetArticleState() {

        currentPage = 0;
        scrollListener.resetState();
        articles.clear();
        articlesAdapter.notifyAdapterDataSetChanged();
    }

    public void loadArticles(String newQuery) {

        newsDeskFilter = null;

        loadArticles(newQuery, sortOrder, beginDate, newsDeskFilter, page);
    }

    public void refreshArticles() {

        if (query != null) {
            newsDeskFilter = null;
        }

        isRefreshing = true;
        loadArticles(query, sortOrder, beginDate, newsDeskFilter, page);
    }

    public void loadArticles(
            String query,
            String sortOrder,
            String beginDate,
            String newsDeskFilter,
            String page) {

        resetArticleState();

        if (!isRefreshing) {
            progressBarListener.showProgressBar();
        }

        this.query = query;
        this.sortOrder = sortOrder == null ? KEY_NEWEST : sortOrder ;
        this.beginDate = beginDate;
        this.newsDeskFilter = newsDeskFilter;
        this.page = page;

        client.getArticles(
                query,
                sortOrder,
                beginDate,
                newsDeskFilter,
                page,
                new Callback<QueryResponse>() {
                    @Override
                    public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {

                        if (response.isSuccessful()) {
                            QueryResponse queryResponse = response.body();

                            articles.addAll(Article.fromQueryResponse(queryResponse));

                            articlesAdapter.notifyAdapterDataSetChanged();

                            progressBarListener.hideProgressBar();
                            scrollToTopListener.scrollToTop();

                            if (isRefreshing) {
                                refreshListener.finishRefreshing();
                                isRefreshing = false;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<QueryResponse> call, Throwable t) {

                        if (Utils.isNetworkAvailable(context)) {
                            emptyViewListener.showEmptyView();
                        } else {
                            emptyViewListener.showNetworkDisconnectedView();
                        }

                        Log.e(TAG, ERROR);
                        t.printStackTrace();
                    }
                }
        );
    }
}
