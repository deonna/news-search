package com.deonna.newssearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.adapters.ArticlesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.wvArticle) WebView wvArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(ArticlesAdapter.KEY_URL);

        wvArticle.loadUrl(url);
    }
}
