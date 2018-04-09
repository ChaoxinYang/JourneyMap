package com.example.chaoxin.journeymap.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chaoxin on 2018-04-07.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PlaceStory.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + PlaceStoryContract.PlaceStoryEntry.TABLE_NAME + "(" +
                    PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE + " TEXT, "
                    + PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY + " TEXT, "
                    + PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH + " TEXT )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlaceStoryContract.PlaceStoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
