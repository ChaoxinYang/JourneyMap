package com.example.chaoxin.journeymap.data.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chaoxin.journeymap.data.local.DatabaseHelper;
import com.example.chaoxin.journeymap.data.local.PlaceStoryContract;
import com.example.chaoxin.journeymap.data.model.PlaceObject;

/**
 * Created by chaoxin on 2018-04-07.
 */

public class PlaceStoryRepository {
    private SQLiteDatabase mDatabase;
    private final DatabaseHelper mDBHelper;
    private final String[] PLACESTORY_ALL_COLUMNS = {
            PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID,
            PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE,
            PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY,
            PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH,
    };


    // Opens the mDatabase to use it
    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    // Closes the mDatabase when you no longer need it
    public void close() {
        mDBHelper.close();
    }

    public PlaceStoryRepository(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }


    public void save(PlaceObject placeStory) {
        ContentValues values = new ContentValues();
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE, placeStory.getmPlaceName());
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY, placeStory.getmPlaceStory());
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH, placeStory.getmImagePath());
        mDatabase.insert(PlaceStoryContract.PlaceStoryEntry.TABLE_NAME, null, values);
        mDatabase.close();

    }

    public void update(int id,PlaceObject placeStory) {
        ContentValues values = new ContentValues();
        values.put(PlaceStoryContract.PlaceStoryEntry._ID, placeStory.getmId());
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE, placeStory.getmPlaceName());
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY, placeStory.getmPlaceStory());
        values.put(PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH, placeStory.getmImagePath());
        mDatabase.update(PlaceStoryContract.PlaceStoryEntry.TABLE_NAME, values,PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(id)});
        mDatabase.close();

    }

    public Cursor findAll() {
        return mDatabase.query(PlaceStoryContract.PlaceStoryEntry.TABLE_NAME, PLACESTORY_ALL_COLUMNS, null, null, null, null, null);
    }

    public void delete(int id) {

        mDatabase.delete(PlaceStoryContract.PlaceStoryEntry.TABLE_NAME, PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID + " =?",
                new String[]{Integer.toString(id)});

    }

}


