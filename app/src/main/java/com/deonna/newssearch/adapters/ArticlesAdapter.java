package com.deonna.newssearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deonna.newssearch.R;
import com.deonna.newssearch.activities.ChromeArticleActivity;
import com.deonna.newssearch.databinding.ItemArticleImageBinding;
import com.deonna.newssearch.databinding.ItemArticleNoImageBinding;
import com.deonna.newssearch.listeners.EmptyViewListener;
import com.deonna.newssearch.models.Article;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String KEY_URL = "url";
    private static final int RESIZE_VALUE = 200;

    private static final int ARTICLE_IMAGE = 0;
    private static final int ARTICLE_NO_IMAGE = 1;

    private Context context;
    private List<Article> articles;

    private EmptyViewListener emptyViewListener;

    public ArticlesAdapter(Context context, List<Article> articles, EmptyViewListener emptyViewListener) {

        this.context = context;
        this.articles = articles;

        this.emptyViewListener = emptyViewListener;
    }

    public void notifyAdapterDataSetChanged() {

        super.notifyDataSetChanged();

        if (articles.isEmpty()) {
            this.emptyViewListener.showEmptyView();
        } else {
            this.emptyViewListener.showNormalView();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case ARTICLE_IMAGE:
                ItemArticleImageBinding articleViewDataBinding = DataBindingUtil.inflate(LayoutInflater
                        .from(parent.getContext
                                ()), R.layout.item_article_image, parent, false);
                viewHolder = new ArticlesViewHolder(articleViewDataBinding);
                break;
            case ARTICLE_NO_IMAGE:
                ItemArticleNoImageBinding noImageViewDataBinding = DataBindingUtil.inflate(LayoutInflater
                        .from(parent.getContext
                                ()), R.layout.item_article_no_image, parent, false);
                viewHolder = new ArticlesNoImageViewHolder(noImageViewDataBinding);
                break;
            default:
                ItemArticleNoImageBinding defaultViewDataBinding = DataBindingUtil.inflate
                        (LayoutInflater
                        .from(parent.getContext
                                ()), R.layout.item_article_no_image, parent, false);
                viewHolder = new ArticlesNoImageViewHolder(defaultViewDataBinding);
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
            return ARTICLE_IMAGE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Article article = articles.get(position);

        switch(holder.getItemViewType()) {

            case ARTICLE_IMAGE:
                ArticlesViewHolder articlesHolder = (ArticlesViewHolder) holder;
                articlesHolder.binding.setArticle(article);
                articlesHolder.binding.executePendingBindings();
                loadImage(article, articlesHolder.binding.ivThumbnail);
                break;
            case ARTICLE_NO_IMAGE:
                ArticlesNoImageViewHolder noImageHolder = (ArticlesNoImageViewHolder) holder;
                noImageHolder.binding.setArticle(article);
                noImageHolder.binding.executePendingBindings();
            default:
                ArticlesNoImageViewHolder defaultHolder = (ArticlesNoImageViewHolder) holder;
                defaultHolder.binding.setArticle(article);
                defaultHolder.binding.executePendingBindings();
                break;
        }
    }

    private void loadImage(Article article, ImageView ivThumbnail) {

        ivThumbnail.setImageResource(0);

        Glide
                .with(context)
                .load(article.thumbnail)
                .override(RESIZE_VALUE, RESIZE_VALUE)
                .placeholder(R.drawable.ic_vector_image_placeholder)
                .into(ivThumbnail);
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        final ItemArticleImageBinding binding;

        public ArticlesViewHolder(ItemArticleImageBinding itemView) {

            super(itemView.getRoot());

            binding = itemView;

            binding.cvArticle.setOnClickListener(v -> {
                openArticle();
            });
        }

        public void openArticle() {

            Context context = binding.cvArticle.getContext();

            Intent intent = new Intent(context, ChromeArticleActivity.class);
            intent.putExtra(KEY_URL, binding.getArticle().url);

            context.startActivity(intent);
        }
    }

    public static class ArticlesNoImageViewHolder extends RecyclerView.ViewHolder {

        final ItemArticleNoImageBinding binding;

        public ArticlesNoImageViewHolder(ItemArticleNoImageBinding itemView) {

            super(itemView.getRoot());

            binding = itemView;

            binding.cvArticle.setOnClickListener(v -> {
                openArticle();
            });
        }

        public void openArticle() {

            Context context = binding.cvArticle.getContext();

            Intent intent = new Intent(context, ChromeArticleActivity.class);
            intent.putExtra(KEY_URL, binding.getArticle().url);

            context.startActivity(intent);
        }
    }
}
