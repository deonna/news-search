package com.deonna.newssearch.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.deonna.newssearch.R;
import com.deonna.newssearch.adapters.ArticlesAdapter;

public class ChromeArticleActivity extends AppCompatActivity {

    private static final String SEND_DATA_TYPE = "text/plain";
    private static final String SEND_INTENT_DESCRIPTION = "Share Link";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(ArticlesAdapter.KEY_URL);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        addSendIntent(builder, url);
        addToolbarComponents(builder);

        launchChromeWindow(builder, url);
    }

    private void addSendIntent(CustomTabsIntent.Builder builder, String url) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_share);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType(SEND_DATA_TYPE);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                1,
                sendIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder.setActionButton(bitmap, SEND_INTENT_DESCRIPTION, pendingIntent, true);
    }

    private void addToolbarComponents(CustomTabsIntent.Builder builder) {

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.addDefaultShareMenuItem();
    }

    private void launchChromeWindow(CustomTabsIntent.Builder builder, String url) {

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
