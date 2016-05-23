package com.example.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("log", "--- onCreate database ---");
        db.execSQL("create table git_users_table ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "login text,"
                + "email text,"
                + "company text,"
                + "repositories text,"
                + "followers number,"
                + "following number"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
