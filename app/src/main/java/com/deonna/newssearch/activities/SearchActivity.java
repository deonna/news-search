package com.deonna.newssearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.adapters.ArticlesAdapter;
import com.deonna.newssearch.fragments.FilterFragment;
import com.deonna.newssearch.listeners.ArticleFilterListener;
import com.deonna.newssearch.listeners.ArticleQueryListener;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.network.NewYorkTimesClient;
import com.deonna.newssearch.utilities.ArticleLoader;
import com.deonna.newssearch.utilities.EndlessScrollHandler;
import com.deonna.newssearch.utilities.FilterPositions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends AppCompatActivity implements ArticleFilterListener {

    private static final int NUM_COLUMNS = 2;
    private static final String QUERY_HINT = "Enter a search query...";

    @BindView(R.id.tbArticles) Toolbar tbArticles;
    @BindView(R.id.rvArticles) RecyclerView rvArticles;

    @BindView(R.id.svArticle) SearchView svArticle;

    @BindView(R.id.ivFilter) ImageView ivFilter;

    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;

    private EndlessScrollHandler endlessScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(tbArticles);

        initializeArticleList();

        svArticle.setQueryHint(QUERY_HINT);
        ArticleQueryListener articleQueryListener = new ArticleQueryListener(svArticle, endlessScrollHandler);
//        initializeSidebar();
    }

    private void initializeArticleList() {

        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(this, articles);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, StaggeredGridLayoutManager
                .VERTICAL);

        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(layoutManager);

        endlessScrollHandler = new EndlessScrollHandler(articles, articlesAdapter, layoutManager);
        rvArticles.addOnScrollListener(endlessScrollHandler.scrollListener);
    }

    @OnClick(R.id.ivFilter)
    public void openFilterDialog() {

        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance("Set Filters");
        filterFragment.show(fm, "fragment_filter");
    }

    @Override
    public void onApplyFilters(String beginDate, String sortOrderParam, Map<String, Boolean> topics) {
        // construct query with all these params
    }

//    private ActionBarDrawerToggle drawerToggle;

//    @BindView(R.id.dlOptions) DrawerLayout dlOptions;
//    @BindView(R.id.nvDrawer) NavigationView nvDrawer;

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//
//        switch (item.getItemId()) {
//            case android.R.id.options:
//                dlOptions.openDrawer(GravityCompat.START);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setQueryHint(QUERY_HINT);
//
//        articleQueryListener = new ArticleQueryListener(searchView, endlessScrollHandler);
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    private void initializeSidebar() {
//
//        drawerToggle = setupDrawerToggle();
//        dlOptions.addDrawerListener(drawerToggle);
//
//        //initializeDrawerContent();
//    }

//    private void initializeDrawerContent() {
//
//        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//           @Override
//           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//               selectDrawerItem(item);
//               return true;
//           }
//
//        });
//    }

//    private void selectDrawerItem(MenuItem item) {
//
//        View view = item.getActionView();
//
//
//        switch(item.getItemId()) {
//
//            case R.id.vwBeginDate:
//                ImageView ivBeginDate = ButterKnife.findById(view, R.id.ivBeginDate);
//                break;
//            case R.id.vwSortOrder:
//                Spinner spSortOrder = ButterKnife.findById(view, R.id.spSortOrder);
//                break;
//            case R.id.vwArts:
//                CheckBox cbArts = ButterKnife.findById(view, R.id.cbTopic);
//                cbArts.setChecked(!cbArts.isChecked());
//                break;
//            case R.id.vwFashion:
//                CheckBox cbFashion = ButterKnife.findById(view, R.id.cbTopic);
//                cbFashion.setChecked(!cbFashion.isChecked());
//                break;
//            case R.id.vwSports:
//                CheckBox cbSports = ButterKnife.findById(view, R.id.cbTopic);
//                cbSports.setChecked(!cbSports.isChecked());
//                break;
//
//        }
//    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onPostCreate(savedInstanceState);
//
//        drawerToggle.syncState();
//    }

//    private ActionBarDrawerToggle setupDrawerToggle() {
//
//        return new ActionBarDrawerToggle(this, dlOptions, tbArticles, R.string.drawer_open,  R.string.drawer_close);
//    }
}
