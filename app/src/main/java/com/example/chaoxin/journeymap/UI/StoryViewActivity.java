package com.example.chaoxin.journeymap.UI;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaoxin.journeymap.R;
import com.example.chaoxin.journeymap.data.model.PlaceObject;

/**
 * Created by chaoxin on 2018-04-09.
 */

public class StoryViewActivity extends AppCompatActivity {

    private TextView storyTitle;
    private TextView storyContent;
    private ImageView storyImage;
    private PlaceObject placeStory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_view);
        initializeView();

    }

    private void initializeView() {
        //Find the views by id in order to initialize these with the information we get from the
        //placeStory Object selecet by the cursor.
        storyContent = findViewById(R.id.viewStoryContent);
        storyTitle = findViewById(R.id.viewStoryTitle);
        storyImage = findViewById(R.id.viewStoryImage);
        //Get the information about the selected placeStory Object en inflate our views with tit.
        placeStory = (PlaceObject) getIntent().getSerializableExtra("placeStory");
        storyContent.setText(placeStory.getmPlaceStory());
        storyTitle.setText(placeStory.getmPlaceName());
        storyImage.setImageBitmap(BitmapFactory.decodeFile(placeStory.getmImagePath()));
    }

}
