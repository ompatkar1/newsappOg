package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SavedViewModel extends ViewModel {
    private LiveData<List<NewsArticle>> newsArticlesLiveData;

    public LiveData<List<NewsArticle>> getNewsArticlesLiveData(AppDatabase database) {
        if (newsArticlesLiveData == null) {
            // Replace this with the actual code to fetch data from your data source (e.g., Room database)
            newsArticlesLiveData = MyApp.database.newsArticleDao().getAllNewsArticles(); // Use your repository or data access method.
        }
        return newsArticlesLiveData;
    }
}

