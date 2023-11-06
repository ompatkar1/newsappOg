package com.example.myapplication;

import android.app.Application;
import androidx.room.Room;

public class MyApp extends Application {
    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "news-database.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
