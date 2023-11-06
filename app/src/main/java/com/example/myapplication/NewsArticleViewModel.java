package com.example.myapplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NewsArticleViewModel extends ViewModel {
    private LiveData<List<NewsArticle>> newsArticles;
    private NewsArticleDao newsArticleDao;

    public NewsArticleViewModel(Context context) {
        newsArticleDao = AppDatabase.getInstance(context).newsArticleDao();
        newsArticles = newsArticleDao.getAllNewsArticles();
    }

    public LiveData<List<NewsArticle>> getNewsArticles() {
        return newsArticles;
    }
}
