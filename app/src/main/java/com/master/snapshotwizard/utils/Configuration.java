package com.master.snapshotwizard.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.activities.ActivityWithSwitchHandler;

import java.io.File;
import java.net.MalformedURLException;
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

    public void configureList(ActivityWithSwitchHandler activity, String activityName){
        Log.d("ListConfiguration", "starting configureList");
        this.currActivity = activity;
        switch (activityName){
            case "Operations":
                configureOperations(currActivity);
                break;
            case "Save":
                configureSave(currActivity);
                break;
            case "CompressResize":
                configureCompressResize(currActivity);
                break;
            default:
                Log.d("ListConfiguration", "switch EndedInDefault");
        }
        Log.d("ListConfiguration", "ending configureList");
    }


    private void configureOperations(ActivityWithSwitchHandler activity){
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
    }

    private void configureSave(ActivityWithSwitchHandler activity){
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(activity, R.string.save_save_title), getText(activity, R.string.save_save_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_compress_title), getText(activity, R.string.save_compress_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_share_title), getText(activity, R.string.save_share_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_script_title), getText(activity, R.string.save_script_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_google_title), getText(activity, R.string.save_google_desc)));

        RecyclerView recyclerView = activity.findViewById(R.id.save_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        listRecycleViewAdapater = new ListRecycleViewAdapater(activity, listDataset);
        listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(listRecycleViewAdapater);
    }


    private void configureCompressResize(ActivityWithSwitchHandler currActivity) {
        ArrayList<File> listDataset = ElementGrabber.getSavedFiles();

    }

    private static String getText(AppCompatActivity activity, int id) {
        return activity.getResources().getString(id);
    }

    @Override
    public void onItemClick(View view, int position) throws MalformedURLException {
        Log.d(this, "You clicked " + listRecycleViewAdapater.getItem(position) + " on row number " + position);
        currActivity.switchHandler(view, position);
    }
}
