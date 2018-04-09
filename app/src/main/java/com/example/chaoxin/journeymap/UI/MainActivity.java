package com.example.chaoxin.journeymap.UI;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

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

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100L);
        itemAnimator.setRemoveDuration(100L);
        mRecyclerView.setItemAnimator(itemAnimator);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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
    private ItemTouchHelper.SimpleCallback itemTouchCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // unused
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Get the id of the game object on which we performed the swipe operation
                PlaceObject placeStory = ((PlaceStoriesAdapter.PlaceObjectViewHolder) viewHolder).getmPlaceStory();
                // Delete the game object from our database
                mDataSource.delete(placeStory.getmId());
                // Get a new cursor from our database
                Cursor cursor = mDataSource.findAll();
                mAdapter.swapCursor(cursor);
                mAdapter.notifyDataSetChanged();
                // Show a Toast message to inform the user that the game was deleted, note that we
                // are calling makeText from within an anonymous class so we have to explicitly tell
                // it to use GamesActivity.this instead of just this as that points to the anonymous
                // class
                Toast.makeText(MainActivity.this, R.string.message_story_deleted, Toast.LENGTH_SHORT).show();
            }
        };
    }
}

