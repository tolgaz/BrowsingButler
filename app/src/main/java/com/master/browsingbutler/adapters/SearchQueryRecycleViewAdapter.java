package com.master.browsingbutler.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.browsingbutler.models.ElementWrapper;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams;

public class SearchQueryRecycleViewAdapter extends RecyclerView.Adapter<SearchQueryRecycleViewAdapter.SearchQueryRecycleViewHolder> {
    private List<ElementWrapper> listDataset;
    private LayoutInflater mInflater;
    private ListRecycleViewAdapter.ItemClickListener mClickListener;
    private List<SearchQueryRecycleViewAdapter.SearchQueryRecycleViewHolder> viewHolders = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // stores and recycles views as they are scrolled off screen
    public class SearchQueryRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        CheckedTextView checkedTextView;

        SearchQueryRecycleViewHolder(View itemView) {
            super(itemView);
            this.checkedTextView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (SearchQueryRecycleViewAdapter.this.mClickListener != null) {
                SearchQueryRecycleViewAdapter.this.mClickListener.onItemClick(view, this.getAdapterPosition());
                SearchQueryRecycleViewAdapter.this.listDataset.get(this.getAdapterPosition()).setChosen(!this.checkedTextView.isChecked());
                this.checkedTextView.setChecked(!this.checkedTextView.isChecked());

            }
        }

        public void select() {
            this.checkedTextView.setChecked(true);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchQueryRecycleViewAdapter(Context context, List<ElementWrapper> listDataset) {
        this.mInflater = LayoutInflater.from(context);
        this.listDataset = listDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SearchQueryRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mInflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        SearchQueryRecycleViewHolder searchQueryRecycleViewHolder = new SearchQueryRecycleViewHolder(view);
        this.viewHolders.add(searchQueryRecycleViewHolder);
        return searchQueryRecycleViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SearchQueryRecycleViewHolder holder, int position) {
        ElementWrapper elementWrapper = this.listDataset.get(position);
        CheckedTextView checkedTextView = holder.checkedTextView;

        checkedTextView.setChecked(elementWrapper.getChosen());

        checkedTextView.setText(elementWrapper.getText());
        checkedTextView.setMaxLines(3);
        checkedTextView.setEllipsize(TextUtils.TruncateAt.END);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) checkedTextView.getLayoutParams();
        params.height = LayoutParams.WRAP_CONTENT;
        params.setMargins(0, 0, 0, 15);
        checkedTextView.setLayoutParams(params);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.listDataset.size();
    }

    public List<SearchQueryRecycleViewAdapter.SearchQueryRecycleViewHolder> getViewHolders() {
        return this.viewHolders;
    }

    // allows clicks events to be caught
    public void setClickListener(ListRecycleViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}