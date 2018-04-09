package com.example.chaoxin.journeymap.UI;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chaoxin.journeymap.R;
import com.example.chaoxin.journeymap.data.model.PlaceObject;
import com.example.chaoxin.journeymap.data.provider.PlaceStoryRepository;

public class MainActivity extends AppCompatActivity implements PlaceStoriesAdapter.OnPlaceStoryClickListener {
    private PlaceStoriesAdapter mAdapter;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private PlaceStoryRepository mDataSource;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabView;
    private PlaceObject currentSelectPlaceStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView setup
        mRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2); //2 cells per row
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataSource = new PlaceStoryRepository(this);
        mDataSource.open();

        updateUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fabEdit = findViewById(R.id.fabEdit);
        fabView = findViewById(R.id.fabView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNewStory();
            }
        });
    }

    private void updateUI() {
        mCursor = mDataSource.findAll();
        if (mAdapter == null) {
            mAdapter = new PlaceStoriesAdapter(mCursor, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapCursor(mCursor);
            fabEdit.setVisibility(View.GONE);
            fabView.setVisibility(View.GONE);
            currentSelectPlaceStory = null;
        }

    }

    protected void onResume() {
        super.onResume();
        mDataSource.open();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null && !mCursor.isClosed()) mCursor.close();
        mDataSource.close();
    }

    private void insertNewStory() {
        Intent intent = new Intent(this, StoryModifyActivity.class);
        intent.setAction(Intent.ACTION_INSERT);
        startActivity(intent);
    }

    private void updateStory(PlaceObject placeStory) {
        Intent intent = new Intent(this, StoryModifyActivity.class);

        intent.putExtra("placeStory", placeStory);
        intent.setAction(Intent.ACTION_EDIT);
        startActivity(intent);
    }
    private void viewStory(PlaceObject placeStory) {
        Intent intent = new Intent(this, StoryViewActivity.class);

        intent.putExtra("placeStory", placeStory);
        intent.setAction(Intent.ACTION_EDIT);
        startActivity(intent);
    }
    @Override
    public void onPlaceStoryClick(PlaceObject placeStory) {
        fabEdit.setVisibility(View.VISIBLE);
        fabView.setVisibility(View.VISIBLE);
        currentSelectPlaceStory = placeStory;
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStory(currentSelectPlaceStory);
            }
        });
        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewStory(currentSelectPlaceStory);
            }
        });
    }
}
