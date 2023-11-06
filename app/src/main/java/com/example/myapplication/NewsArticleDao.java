package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsArticleDao {
    @Insert
    void insert(NewsArticle newsArticle);

    @Query("SELECT * FROM news_articles")
    LiveData<List<NewsArticle>> getAllNewsArticles();



}
