package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_articles")
public class NewsArticle {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String source;
    public String imageUrl;
    public String url;
}
