package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder> {
    private List<NewsArticle> newsArticles;
    private Context context;

    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        this.context = context;
        this.newsArticles = newsArticles;
    }

    public void setData(List<NewsArticle> newData) {
        newsArticles = newData;
        notifyDataSetChanged();
    }

    @Override
    public NewsArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_news_item, parent, false);
        return new NewsArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsArticleViewHolder holder, int position) {
        NewsArticle newsArticle = newsArticles.get(position);
        holder.titleTextView.setText(newsArticle.title);
        holder.sourceTextView.setText(newsArticle.source);
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_hide_image_24); // You can specify a placeholder image

        Glide.with(holder.itemView.getContext())
                .load(newsArticle.imageUrl)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
        // Load the image using an image loading library like Picasso or Glide
        // For example, if using Picasso:
        // Picasso.get().load(newsArticle.imageUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public class NewsArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView sourceTextView;
        public ImageView imageView;

        public NewsArticleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sourceTextView = itemView.findViewById(R.id.sourceTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
