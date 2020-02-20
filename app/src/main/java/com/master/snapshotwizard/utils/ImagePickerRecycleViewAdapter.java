package com.master.snapshotwizard.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.master.snapshotwizard.R;
import com.master.snapshotwizard.models.ElementWrapper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class ImagePickerRecycleViewAdapter extends RecyclerView.Adapter<ImagePickerRecycleViewAdapter.ImagePickerRecycleViewHolder> {
    private ArrayList<ElementWrapper> fileDataset;
    private LayoutInflater mInflater;
    private ListRecycleViewAdapter.ItemClickListener mClickListener;
    private ArrayList<ImagePickerRecycleViewHolder> viewHolders = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // stores and recycles views as they are scrolled off screen
    public class ImagePickerRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        ImageView imageView;
        ImageView imageTick;

        ImagePickerRecycleViewHolder(View itemView, boolean visible) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_preview);
            this.imageTick = itemView.findViewById(R.id.image_tick);
            this.imageTick.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                try {
                    mClickListener.onItemClick(view, getAdapterPosition());
                    flipVisibility();
                } catch (MalformedURLException e) {
                    Log.d(this, "Wrong URL. " + Arrays.toString(e.getStackTrace()));
                }
            }
        }

        public void select(){
            imageTick.setVisibility(View.VISIBLE);
        }

        private void flipVisibility(){
            imageTick.setVisibility(imageTick.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    ImagePickerRecycleViewAdapter(Context context, ArrayList<ElementWrapper> fileDataset) {
        this.mInflater = LayoutInflater.from(context);
        this.fileDataset = fileDataset;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ImagePickerRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_picker, parent, false);
        ImagePickerRecycleViewHolder imagePickerRecycleViewHolder = new ImagePickerRecycleViewHolder(view, fileDataset.get(viewHolders.size()).getChosen());
        viewHolders.add(imagePickerRecycleViewHolder);
        return imagePickerRecycleViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ImagePickerRecycleViewHolder holder, int position) {
        File file = fileDataset.get(position).getFile();
        URI url = file.toURI();
        if(url.getPath().contains(".mp4")){
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(url.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
            holder.imageView.setImageBitmap(thumbnail);
        } else{
            holder.imageView.setImageURI(Uri.parse(url.getPath()));
        }
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        if(layoutParams instanceof FlexboxLayoutManager.LayoutParams){
            ((FlexboxLayoutManager.LayoutParams) layoutParams).setFlexGrow(1f);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return fileDataset.size();
    }

    public ArrayList<ElementWrapper> getFileDataset(){
        return fileDataset;
    }

    public ArrayList<ImagePickerRecycleViewHolder> getViewHolders() {
        return viewHolders;
    }

    // convenience method for getting data at click position
    File getItem(int id) {
        return fileDataset.get(id).getFile();
    }

    // allows clicks events to be caught
    void setClickListener(ListRecycleViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}