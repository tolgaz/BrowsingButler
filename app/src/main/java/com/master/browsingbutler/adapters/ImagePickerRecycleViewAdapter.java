package com.master.browsingbutler.adapters;

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
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerRecycleViewAdapter extends RecyclerView.Adapter<ImagePickerRecycleViewAdapter.ImagePickerRecycleViewHolder> {
    private List<ElementWrapper> fileDataset;
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
            if (ImagePickerRecycleViewAdapter.this.mClickListener != null) {
                ImagePickerRecycleViewAdapter.this.mClickListener.onItemClick(view, this.getAdapterPosition());
                this.flipVisibility();
            }
        }

        public void select() {
            this.imageTick.setVisibility(View.VISIBLE);
        }

        private void flipVisibility() {
            this.imageTick.setVisibility(this.imageTick.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ImagePickerRecycleViewAdapter(Context context, List<ElementWrapper> fileDataset) {
        this.mInflater = LayoutInflater.from(context);
        this.fileDataset = fileDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ImagePickerRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mInflater.inflate(R.layout.image_picker, parent, false);
        ImagePickerRecycleViewHolder imagePickerRecycleViewHolder = new ImagePickerRecycleViewHolder(view, this.fileDataset.get(this.viewHolders.size()).getChosen());
        this.viewHolders.add(imagePickerRecycleViewHolder);
        return imagePickerRecycleViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ImagePickerRecycleViewHolder holder, int position) {
        File file = this.fileDataset.get(position).getFile();
        URI url = file.toURI();
        if (url.getPath().contains(".mp4")) {
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(url.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
            holder.imageView.setImageBitmap(thumbnail);
        } else {
            holder.imageView.setImageURI(Uri.parse(url.getPath()));
        }
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        if (layoutParams instanceof FlexboxLayoutManager.LayoutParams) {
            ((FlexboxLayoutManager.LayoutParams) layoutParams).setFlexGrow(1f);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.fileDataset.size();
    }

    public List<ElementWrapper> getFileDataset() {
        return this.fileDataset;
    }

    public ArrayList<ImagePickerRecycleViewHolder> getViewHolders() {
        return this.viewHolders;
    }

    // allows clicks events to be caught
    public void setClickListener(ListRecycleViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}