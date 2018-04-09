package com.example.chaoxin.journeymap.data.model;

import java.io.Serializable;

/**
 * Created by chaoxin on 2018-03-25.
 */

public class PlaceObject implements Serializable {
    private int mId;
    private String mPlaceName;
    private String mPlaceStory;
    private String mImagePath;

    public PlaceObject() {
    }

    public PlaceObject(String mPlaceName, String mPlaceStory, String mImagePath) {
        this(-1, mPlaceName, mPlaceStory, mImagePath);

    }

    public PlaceObject(int id, String mPlaceName, String mPlaceStory, String mImagePath) {
        this.mId = id;
        this.mPlaceName = mPlaceName;
        this.mPlaceStory = mPlaceStory;
        this.mImagePath = mImagePath;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getmPlaceStory() {
        return mPlaceStory;
    }

    public void setmPlaceStory(String mPlaceStory) {
        this.mPlaceStory = mPlaceStory;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

}
