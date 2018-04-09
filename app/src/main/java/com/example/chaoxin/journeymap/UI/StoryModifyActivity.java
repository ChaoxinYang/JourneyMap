package com.example.chaoxin.journeymap.UI;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.chaoxin.journeymap.data.model.PlaceObject;
import com.example.chaoxin.journeymap.R;
import com.example.chaoxin.journeymap.data.provider.PlaceStoryRepository;

import java.util.Objects;


public class StoryModifyActivity extends AppCompatActivity {

    private PlaceObject placeStory;
    private TextInputEditText inputTitle;
    private TextInputEditText inputStory;
    private Button imageButton;
    private ImageView imageView;
    private String mImagePath;
    private  static int LOAD_IMAGE_RESULTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_modify);
        initializeView();

    }

    protected void initializeView() {
        // Find all our views from within our layout
        inputTitle = findViewById(R.id.inputTitleContent);
        inputStory = findViewById(R.id.inputStoryContent);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        // Find our FloatingActionButton and tell it to save the homework object when clicked
        FloatingActionButton fab = findViewById(R.id.fabModify);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImage();
            }
        });
        // Based on the action we will add or update a homework
        if (Objects.equals(getIntent().getAction(), Intent.ACTION_INSERT)) {
            // We are adding a new homework
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePlaceStory();
                }
            });
        } else {
            // We are updating an existing homework so start by retrieving it from the intent
            placeStory = (PlaceObject) getIntent().getSerializableExtra("placeStory");
            // Set the values for the views
            inputTitle.setText(placeStory.getmPlaceName());
            inputStory.setText(placeStory.getmPlaceStory());
            imageView.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePlaceStory();
                }
            });
        }
    }

    private void PickImage(){

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            mImagePath = imagePath;

            // Now we need to set the GUI ImageView data with data read from the picked file.
            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }


    /**
     * Takes the values from the view and attempts to save a homework entity within the database.
     * The title and platform input values are required and will result in an error message when
     * empty.
     */
    private void savePlaceStory() {
        String title = inputTitle.getText().toString();
        String story = inputStory.getText().toString();
        String imagePath = mImagePath;

        if (title.isEmpty()) {
            inputTitle.setError(getString(R.string.error_title_required));
        } else if (story.isEmpty()) {
            inputStory.setError(getString(R.string.error_story_required));
        }
        else {
            PlaceObject placeStory = new PlaceObject(title, story, imagePath);

            PlaceStoryRepository repository = new PlaceStoryRepository(this);
            repository.open();
            repository.save(placeStory);

            Toast.makeText(this, R.string.message_story_saved, Toast.LENGTH_LONG).show();

            finish();
        }
    }

    /**
     * Takes the values from the view and attempts to update the homework entity within the database.
     * The title and platform input values are required and will result in an error message when
     * empty.
     */
    private void updatePlaceStory() {
        String title = inputTitle.getText().toString();
        String story = inputStory.getText().toString();
        String imagePath = mImagePath;
        // Validate that the title and platform is not empty
        if (title.isEmpty()) {
            inputTitle.setError(getString(R.string.error_title_required));
        } else if (story.isEmpty()) {
            inputStory.setError(getString(R.string.error_story_required));
        } else {
            PlaceObject updatedPlaceStory = new PlaceObject(title,story,imagePath);

            PlaceStoryRepository repository = new PlaceStoryRepository(this);
            repository.open();
            repository.update(placeStory.getmId(),updatedPlaceStory);

            Toast.makeText(this, R.string.message_story_saved, Toast.LENGTH_LONG).show();

            finish();
        }
    }
}
