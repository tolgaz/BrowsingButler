package com.master.snapshotwizard.utils;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.snapshotwizard.R;

import java.util.ArrayList;

public class Configuration implements ListRecycleViewAdapater.ItemClickListener {
    private ListRecycleViewAdapater listRecycleViewAdapater;

    public void configureToolbar(AppCompatActivity activity, int subtitleId){
        Log.d("ToolbarConfiguration", "configureToolbar");
        androidx.appcompat.widget.Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setSubtitle(activity.getResources().getString(subtitleId));
        activity.setSupportActionBar(toolbar);
    }

    public void configureList(AppCompatActivity activity, String acticityName){
        Log.d("ListConfiguration", "starting configureList");
        switch (acticityName){
            case "Operations":
                ArrayList<ListItem> listDataset = new ArrayList<>();
                listDataset.add(new ListItem(getText(activity, R.string.operations_save_title), getText(activity, R.string.operations_save_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_share_title), getText(activity, R.string.operations_share_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_script_title), getText(activity, R.string.operations_script_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_google_title), getText(activity, R.string.operations_google_desc)));

                RecyclerView recyclerView = activity.findViewById(R.id.operation_recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                listRecycleViewAdapater = new ListRecycleViewAdapater(activity, listDataset);
                listRecycleViewAdapater.setClickListener(this);
                recyclerView.setAdapter(listRecycleViewAdapater);

                break;
            default:
                Log.d("ListConfiguration", "switch EndedInDefault");
        }
        Log.d("ListConfiguration", "ending configureList");
    }

    private static String getText(AppCompatActivity activity, int id) {
        return activity.getResources().getString(id);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(this, "You clicked " + listRecycleViewAdapater.getItem(position) + " on row number " + position);
    }
}
