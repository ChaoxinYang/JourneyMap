package com.example.chaoxin.journeymap.UI;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaoxin.journeymap.R;
import com.example.chaoxin.journeymap.data.local.PlaceStoryContract;
import com.example.chaoxin.journeymap.data.model.PlaceObject;

/**
 * Created by chaoxin on 2018-03-25.
 */
public class PlaceStoriesAdapter extends RecyclerView.Adapter<PlaceStoriesAdapter.PlaceObjectViewHolder> {

    private Cursor cursor;
    private OnPlaceStoryClickListener OnPlaceStoryClickListener;

    public interface OnPlaceStoryClickListener {
        void onPlaceStoryClick(PlaceObject placeStory);
    }

    public PlaceStoriesAdapter(Cursor cursor, OnPlaceStoryClickListener OnPlaceStoryClickListener) {
        this.cursor = cursor;
        this.OnPlaceStoryClickListener = OnPlaceStoryClickListener;
    }

    @Override
    public PlaceObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate our item_game layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_cell, parent, false);
        // Instantiate a GameViewHolder and pass our layout as it's view
        return new PlaceObjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceObjectViewHolder holder, int position) {
        // Move the cursor to the right position
        cursor.moveToPosition(position);
        // Create a game object from the cursor's data
        PlaceObject placeStory = new PlaceObject();
        placeStory.setmId(cursor.getInt(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID)));
        placeStory.setmPlaceName(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE)));
        placeStory.setmPlaceStory(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY)));
        placeStory.setmImagePath(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH)));
        // Bind the bject to the view
        holder.bind(placeStory);
    }

    @Override
    public int getItemCount() {
        return (cursor == null ? 0 : cursor.getCount());
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class PlaceObjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //{
        private PlaceObject mPlaceStory;
        public TextView placeName;
        public TextView placeStory;
        public ImageView placeImage;


        public PlaceObjectViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeTextView);
            placeStory = itemView.findViewById(R.id.placeStoryView);
            placeImage = itemView.findViewById(R.id.placeImageView);
            itemView.setOnClickListener(this);
        }

        public PlaceObject getmPlaceStory() {
            return mPlaceStory;
        }

        void bind(final PlaceObject mPlaceStory) {
            this.mPlaceStory = mPlaceStory;
            placeName.setText(mPlaceStory.getmPlaceName());
            placeStory.setText(mPlaceStory.getmPlaceStory());
            placeImage.setImageBitmap(BitmapFactory.decodeFile(mPlaceStory.getmImagePath()));

        }

        @Override
        public void onClick(View view) {
            cursor.moveToPosition(getAdapterPosition());
            PlaceObject placeStory = new PlaceObject();
            placeStory.setmId(cursor.getInt(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_NAME_ID)));
            placeStory.setmPlaceName(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_TITLE)));
            placeStory.setmPlaceStory(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_STORY)));
            placeStory.setmImagePath(cursor.getString(cursor.getColumnIndex(PlaceStoryContract.PlaceStoryEntry.COLUMN_IMAGEPATH)));


            OnPlaceStoryClickListener.onPlaceStoryClick(placeStory);
        }
    }
}
