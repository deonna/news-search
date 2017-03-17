package com.deonna.newssearch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.deonna.newssearch.R;
import com.deonna.newssearch.adapters.ArticlesAdapter;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.models.articlesearch.QueryResponse;
import com.deonna.newssearch.network.NewYorkTimesClient;
import com.deonna.newssearch.utilities.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final int NUM_COLUMNS = 2;
    private static final String QUERY_HINT = "Enter a search query...";

    @BindView(R.id.tbArticles) Toolbar tbArticles;
    @BindView(R.id.rvArticles) RecyclerView rvArticles;

    @BindView(R.id.dlOptions) DrawerLayout dlOptions;
    @BindView(R.id.nvDrawer) NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    private NewYorkTimesClient client;

    private String currentQuery;
    private int currentPage = 0;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(tbArticles);

        initializeArticleList();
        initializeSidebar();
    }

    private void initializeArticleList() {

        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(this, articles);

        client = new NewYorkTimesClient();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, StaggeredGridLayoutManager
                .VERTICAL);

        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(layoutManager);

        scrollListener = initializeEndlessScrollListener(layoutManager);

        rvArticles.addOnScrollListener(scrollListener);
    }

    private void initializeSidebar() {

        drawerToggle = setupDrawerToggle();
        dlOptions.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, dlOptions, tbArticles, R.string.drawer_open,  R.string.drawer_close);
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

        client.getArticlesByPage("0",
                query,

                new Callback<QueryResponse>() {
                    @Override
                    public void onResponse(Call<QueryResponse> call, retrofit2.Response<QueryResponse> response) {

                        QueryResponse queryResponse = response.body();

                        articles.addAll(Article.fromQueryResponse(queryResponse));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                articlesAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<QueryResponse> call, Throwable t) {

                        Log.d(TAG, "Failed to complete GET request");
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(QUERY_HINT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                currentQuery = query;

                loadArticleByQuery(query);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }


//        switch (item.getItemId()) {
//            case android.R.id.options:
//                dlOptions.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
