package com.deonna.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.activities.ArticleActivity;
import com.deonna.newssearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String KEY_URL = "url";
    private static final int RESIZE_VALUE = 200;

    private Context context;
    private List<Article> articles;

    public ArticlesAdapter(Context context, List<Article> articles) {

        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_article, parent, false);
        viewHolder = new ArticlesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article article = articles.get(position);
        ((ArticlesViewHolder) holder).setArticle(article);

        (((ArticlesViewHolder) holder).ivThumbnail).setImageResource(0);
        Picasso
                .with(context)
                .load(article.thumbnail)
                .resize(RESIZE_VALUE, RESIZE_VALUE)
                .placeholder(R.drawable.thumbnail_placeholder)
                .into(((ArticlesViewHolder) holder).ivThumbnail);


        ((ArticlesViewHolder) holder).tvTitle.setText(article.headline);
        ((ArticlesViewHolder) holder).tvSection.setText(article.section);
        ((ArticlesViewHolder) holder).tvSnippet.setText(article.snippet);
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvArticle) CardView cvArticle;
        @BindView(R.id.ivThumbnail) ImageView ivThumbnail;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvSection) TextView tvSection;
        @BindView(R.id.tvSnippet) TextView tvSnippet;

        private Article article;

        public ArticlesViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setArticle(Article article) {

            this.article = article;
        }

        @OnClick(R.id.cvArticle)
        public void openArticle() {

            Context context = cvArticle.getContext();

            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra(KEY_URL, article.url);

            context.startActivity(intent);
        }
    }
}
