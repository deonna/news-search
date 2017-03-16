package com.deonna.newssearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.models.Article;
import com.deonna.newssearch.models.articlesearch.QueryResponse;
import com.deonna.newssearch.network.NewYorkTimesClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.etQuery) EditText etQuery;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.gvResults) GridView gvResults;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        articles = new ArrayList<>();
    }

    @OnClick(R.id.btnSearch)
    public void onArticleSearch(View view) {

        String query = etQuery.getText().toString();

        NewYorkTimesClient client = new NewYorkTimesClient();

        client.getArticlesFromQuery(
                query,

                new Callback<QueryResponse>() {
                    @Override
                    public void onResponse(Call<QueryResponse> call, retrofit2.Response<QueryResponse> response) {
                        int statusCode = response.code();
                        QueryResponse queryResponse = response.body();

                        Article.addAll(queryResponse);
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
