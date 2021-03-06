package com.master.browsingbutler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ListItem;

import java.util.List;

public class ListRecycleViewAdapter extends RecyclerView.Adapter<ListRecycleViewAdapter.ListRecycleViewHolder> {
    private List<ListItem> listDataset;
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

        ListRecycleViewHolder(View itemView) {
            super(itemView);
            this.firstLine = itemView.findViewById(R.id.first_line);
            this.secondLine = itemView.findViewById(R.id.second_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ListRecycleViewAdapter.this.mClickListener != null) {
                ListRecycleViewAdapter.this.mClickListener.onItemClick(view, this.getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListRecycleViewAdapter(Context context, List<ListItem> listDataset) {
        this.mInflater = LayoutInflater.from(context);
        this.listDataset = listDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mInflater.inflate(R.layout.three_line_list, parent, false);
        return new ListRecycleViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ListRecycleViewHolder holder, int position) {
        ListItem listItem = this.listDataset.get(position);
        holder.firstLine.setText(listItem.getTitle());
        if (listItem.getDescription() == null || listItem.getDescription().isEmpty()) {
            holder.secondLine.setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) holder.firstLine.getLayoutParams();
            marginLayoutParams.bottomMargin = 5;
            holder.firstLine.setLayoutParams(marginLayoutParams);
        } else {
            holder.secondLine.setText(listItem.getDescription());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.listDataset.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}