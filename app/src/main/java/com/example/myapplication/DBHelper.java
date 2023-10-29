package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static String DB_NAME = "profile.db";
    private static int DB_VERSION = 1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    private  static String createTableQuerry = "create table profileUser(name TEXT"+",email TEXT"+",image BLOB)";


    public DBHelper(@Nullable Context context) {
        super(context,DB_NAME,null, DB_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createTableQuerry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeData(ModelClass modelClass){
        SQLiteDatabase database = this.getWritableDatabase();
        Bitmap bitmapImage = modelClass.getImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",modelClass.getName());
        contentValues.put("email",modelClass.getEmail());
        contentValues.put("image",byteImage);

        long checkQuery = database.insert("ProfileUser",null,contentValues);
        if(checkQuery != -1) {
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
            database.close();
        }else{
            Toast.makeText(context, "somethingwent wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor getUser(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor =database.rawQuery("select * from profileUser",null);
        return cursor;

    }
}
