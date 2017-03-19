package com.deonna.newssearch.utilities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.deonna.newssearch.adapters.ArticlesAdapter;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.models.articlesearch.QueryResponse;
import com.deonna.newssearch.network.NewYorkTimesClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleLoader {

    private static final String TAG = ArticleLoader.class.getSimpleName();

    public static final String KEY_NEWEST = "newest";
    public static final String KEY_OLDEST = "oldest";

    public EndlessRecyclerViewScrollListener scrollListener;

    private int currentPage = 0;

    private NewYorkTimesClient client;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    //Current filters
    public String query = null;
    private String sortOrder = null;
    private String beginDate = null;
    private String newsDeskFilter = null;
    private String page = null;

    public ArticleLoader(List<Article> articles, ArticlesAdapter articlesAdapter, StaggeredGridLayoutManager layoutManager) {

        client = new NewYorkTimesClient();

        this.articles = articles;
        this.articlesAdapter = articlesAdapter;

        scrollListener = initializeEndlessScrollListener(layoutManager);
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

                }
        });

        currentPage = page + 1;
        scrollListener.resetState();
    }

    private void notifyArticlesChanged(int newArraySize) {

        int oldCount = articlesAdapter.getItemCount();
        articlesAdapter.notifyItemRangeInserted(oldCount, newArraySize);

        articlesAdapter.notifyDataSetChanged();
    }

    private void resetArticleState() {

        currentPage = 0;
        scrollListener.resetState();
        articles.clear();
        articlesAdapter.notifyDataSetChanged();
    }

    public void loadArticles(String newQuery) {

        newsDeskFilter = null;

        loadArticles(newQuery, sortOrder, beginDate, newsDeskFilter, page);
    }

    public void loadArticles(
            String query,
            String sortOrder,
            String beginDate,
            String newsDeskFilter,
            String page) {

        resetArticleState();

        this.query = query;
        this.sortOrder = sortOrder;
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

                        QueryResponse queryResponse = response.body();

                        articles.addAll(Article.fromQueryResponse(queryResponse));

                        articlesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<QueryResponse> call, Throwable t) {

                    }
                }
        );
    }
}
