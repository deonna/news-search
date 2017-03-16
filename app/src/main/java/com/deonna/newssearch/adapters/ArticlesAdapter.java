package com.deonna.newssearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deonna.newssearch.R;
import com.deonna.newssearch.models.Article;

import java.util.List;

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

    }

    @Override
    public int getItemCount() {

        return articles.size();
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        public ArticlesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
