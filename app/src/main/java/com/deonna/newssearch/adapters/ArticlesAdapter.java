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

import com.bumptech.glide.Glide;
import com.deonna.newssearch.R;
import com.deonna.newssearch.activities.ChromeArticleActivity;
import com.deonna.newssearch.models.Article;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String KEY_URL = "url";
    private static final int RESIZE_VALUE = 200;

    private static final int ARTICLE_DEFAULT = 0;
    private static final int ARTICLE_NO_IMAGE = 1;

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

        switch (viewType) {
            case ARTICLE_DEFAULT:
                View defaultView = inflater.inflate(R.layout.item_article, parent, false);
                viewHolder = new ArticlesViewHolder(defaultView);
                break;
            case ARTICLE_NO_IMAGE:
                View noImageView = inflater.inflate(R.layout.item_article_no_image, parent, false);
                viewHolder = new ArticlesNoImageViewHolder(noImageView);
                break;
            default:
                View view = inflater.inflate(R.layout.item_article, parent, false);
                viewHolder = new ArticlesViewHolder(view);
                break;
        }


        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        Article article = articles.get(position);

        if (article.thumbnail == null) {
            return ARTICLE_NO_IMAGE;
        } {
            return ARTICLE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article article = articles.get(position);

        ArticlesNoImageViewHolder articlesHolder = (ArticlesNoImageViewHolder) holder;
        articlesHolder.setArticle(article);
        articlesHolder.configure();

        switch(holder.getItemViewType()) {

            case ARTICLE_DEFAULT:
                loadImage(article, (ArticlesViewHolder) articlesHolder);
                break;
            default:
                break;
        }
    }

    private void loadImage(Article article, ArticlesViewHolder holder) {

        holder.ivThumbnail.setImageResource(0);

        Glide
                .with(context)
                .load(article.thumbnail)
                .override(RESIZE_VALUE, RESIZE_VALUE)
                .placeholder(R.drawable.thumbnail_placeholder)
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public static class ArticlesViewHolder extends ArticlesNoImageViewHolder {

        @BindView(R.id.ivThumbnail) ImageView ivThumbnail;

        public ArticlesViewHolder(View itemView) {

            super(itemView);
        }
    }

    public static class ArticlesNoImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvArticle) CardView cvArticle;
        @BindView(R.id.tvPublicationDate) TextView tvPublicationDate;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvSection) TextView tvSection;
        @BindView(R.id.tvSnippet) TextView tvSnippet;

        private Article article;

        public ArticlesNoImageViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

            cvArticle.setOnClickListener((view) -> {
                openArticle();
            });
        }

        public void setArticle(Article article) {

            this.article = article;
        }

        public void openArticle() {

            Context context = cvArticle.getContext();

            Intent intent = new Intent(context, ChromeArticleActivity.class);
            intent.putExtra(KEY_URL, article.url);

            context.startActivity(intent);
        }

        public void configure() {

            tvPublicationDate.setText(article.publicationDate);
            tvTitle.setText(article.headline);
            tvSection.setText(article.section);
            tvSnippet.setText(article.snippet);
        }
    }
}
