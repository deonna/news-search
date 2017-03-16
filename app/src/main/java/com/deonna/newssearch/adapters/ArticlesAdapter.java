package com.deonna.newssearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

        Picasso
                .with(context)

                .load(article.thumbnail)
                .placeholder(R.drawable.thumbnail_placeholder)
                .into(((ArticlesViewHolder) holder).ivThumbnail);

        ((ArticlesViewHolder) holder).tvTitle.setText(article.headline);
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail) ImageView ivThumbnail;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public ArticlesViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
