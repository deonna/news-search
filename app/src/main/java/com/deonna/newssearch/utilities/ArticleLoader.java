package com.deonna.newssearch.utilities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

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

    public String currentQuery = "";
    public EndlessRecyclerViewScrollListener scrollListener;

    private int currentPage = 0;

    private NewYorkTimesClient client;
    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

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

        client.getArticlesByPage(Integer.valueOf(page).toString(), currentQuery, new Callback<QueryResponse>() {
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
    }

    public void loadArticleByQuery(String query) {

        resetArticleState();

        client.getArticlesFromQuery(
            query,
            new Callback<QueryResponse>() {
                @Override
                public void onResponse(Call<QueryResponse> call, retrofit2.Response<QueryResponse> response) {

                    QueryResponse queryResponse = response.body();

                    articles.addAll(Article.fromQueryResponse(queryResponse));

                    articlesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<QueryResponse> call, Throwable t) {

                    Log.d(TAG, "Failed to complete GET request");
                }
            }
        );
    }

    public List<Article> loadArticlesNewestToOldest() {

        resetArticleState();

        client.getArticlesSortedNewestToOldest(currentQuery, new Callback<QueryResponse>() {

            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {

                QueryResponse queryResponse = response.body();

                articles.addAll(Article.fromQueryResponse(queryResponse));
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {

            }
        });

        return articles;
    }

    public List<Article> loadArticlesOldestToNewest() {

        resetArticleState();

        client.getArticlesSortedOldestToNewest(currentQuery, new Callback<QueryResponse>() {

            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {

                QueryResponse queryResponse = response.body();

                articles.addAll(Article.fromQueryResponse(queryResponse));
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {

            }
        });

        return articles;
    }

    public void loadArticles(
            String query,
            String sortOrder,
            String beginDate,
            String newsDeskFilter,
            String page) {

        resetArticleState();

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
