package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.NotificationManagerCompat;


public class HomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        searchView = rootView.findViewById(R.id.Search_view);
        recyclerView = rootView.findViewById(R.id.Recycler_view);

        setupRecyclerView();

        btn1 = rootView.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = rootView.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3 = rootView.findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4 = rootView.findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5 = rootView.findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6 = rootView.findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7 = rootView.findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("General", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getNews("General", null);

        return rootView;
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void getNews(String category, String query) {
        NewsApiClient newsApiClient = new NewsApiClient("0e067059ab9347cc86446e6766622193");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .q(query)
                        .category(category)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {


                    @Override
                    public void onSuccess(ArticleResponse response) {
                        if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            Log.d("SearchResults", "Received " + response.getTotalResults() + " results");
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();

                        });
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("GOT FAILURE", throwable.getMessage());
                    }

                }
        );
    }
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String category = btn.getText().toString();
        getNews(category,null);
    }
}