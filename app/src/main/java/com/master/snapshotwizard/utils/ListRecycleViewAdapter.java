package com.master.snapshotwizard.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.models.ListItem;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

class ListRecycleViewAdapter extends RecyclerView.Adapter<ListRecycleViewAdapter.ListRecycleViewHolder> {
    private ArrayList<ListItem> listDataset;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // stores and recycles views as they are scrolled off screen
    public class ListRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView firstLine;
        TextView secondLine;
        ImageButton wrenchButton;

        ListRecycleViewHolder(View itemView) {
            super(itemView);
            firstLine = itemView.findViewById(R.id.first_line);
            secondLine = itemView.findViewById(R.id.second_line);
            wrenchButton = itemView.findViewById(R.id.wrench_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                try {
                    mClickListener.onItemClick(view, getAdapterPosition());
                } catch (MalformedURLException e) {
                    Log.d(this, "Wrong URL. " + Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    ListRecycleViewAdapter(Context context, ArrayList<ListItem> listDataset) {
        this.mInflater = LayoutInflater.from(context);
        this.listDataset = listDataset;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.three_line_list, parent, false);
        return new ListRecycleViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ListRecycleViewHolder holder, int position) {
        ListItem listItem = listDataset.get(position);
        holder.firstLine.setText(listItem.getTitle());
        holder.secondLine.setText(listItem.getDescription());
        if(listItem.getTitle().contains("Apply script")) holder.wrenchButton.setVisibility(View.VISIBLE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listDataset.size();
    }

    // convenience method for getting data at click position
    ListItem getItem(int id) {
        return listDataset.get(id);
    }
    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position) throws MalformedURLException;
    }
}