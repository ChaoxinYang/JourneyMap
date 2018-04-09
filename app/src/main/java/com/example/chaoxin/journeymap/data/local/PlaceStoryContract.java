package com.example.chaoxin.journeymap.data.local;

import android.provider.BaseColumns;

/**
 * Created by chaoxin on 2018-04-07.
 */

public class PlaceStoryContract {

    private PlaceStoryContract() {
    }

    public static class PlaceStoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "PlaceStory";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_STORY = "story";
        public static final String COLUMN_IMAGEPATH = "imagePath";

    }
}
