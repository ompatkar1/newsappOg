package com.example.myapplication;

import static com.example.myapplication.MyApp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.room.Dao;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsArticleAdapter adapter;
    private AppDatabase database; // Your Room database instance

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get your Room database instance (replace with your actual Room database initialization)
        database = Room.databaseBuilder(requireContext(), AppDatabase.class, "news_article_db").build();

        // Create the ViewModel and pass the database instance
        SavedViewModel viewModel = new ViewModelProvider(this).get(SavedViewModel.class);
        LiveData<List<NewsArticle>> newsArticlesLiveData = viewModel.getNewsArticlesLiveData(database);

        // Observe the LiveData for data changes
        newsArticlesLiveData.observe(getViewLifecycleOwner(), newsArticles -> {
            adapter.setData(newsArticles);
        });

        // Initialize the adapter
        adapter = new NewsArticleAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}









