package com.master.snapshotwizard.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.activities.ActivityWithSwitchHandler;

import java.util.ArrayList;

public class Configuration implements ListRecycleViewAdapater.ItemClickListener {
    private ListRecycleViewAdapater listRecycleViewAdapater;
    private ActivityWithSwitchHandler currActivity;

    public void configureToolbar(AppCompatActivity activity, int subtitleId){
        Log.d("ToolbarConfiguration", "configureToolbar");
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setSubtitle(activity.getResources().getString(subtitleId));
        activity.setSupportActionBar(toolbar);
    }

    public void configureList(ActivityWithSwitchHandler activity, String acticityName){
        Log.d("ListConfiguration", "starting configureList");
        this.currActivity = activity;
        ArrayList<ListItem> listDataset;
        RecyclerView recyclerView;
        switch (acticityName){
            case "Operations":
                listDataset = new ArrayList<>();
                listDataset.add(new ListItem(getText(activity, R.string.operations_save_title), getText(activity, R.string.operations_save_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_share_title), getText(activity, R.string.operations_share_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_script_title), getText(activity, R.string.operations_script_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.operations_google_title), getText(activity, R.string.operations_google_desc)));

                recyclerView = activity.findViewById(R.id.operation_recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                listRecycleViewAdapater = new ListRecycleViewAdapater(activity, listDataset);
                listRecycleViewAdapater.setClickListener(this);
                recyclerView.setAdapter(listRecycleViewAdapater);
                break;
            case "Save":
                listDataset = new ArrayList<>();
                listDataset.add(new ListItem(getText(activity, R.string.save_save_title), getText(activity, R.string.save_save_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.save_compress_title), getText(activity, R.string.save_compress_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.save_share_title), getText(activity, R.string.save_share_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.save_script_title), getText(activity, R.string.save_script_desc)));
                listDataset.add(new ListItem(getText(activity, R.string.save_google_title), getText(activity, R.string.save_google_desc)));

                recyclerView = activity.findViewById(R.id.save_recycle_view);
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
        currActivity.switchHandler(view, position);
    }
}
