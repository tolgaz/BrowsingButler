package com.master.snapshotwizard.components;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.master.snapshotwizard.R;
import com.master.snapshotwizard.adapters.ImagePickerRecycleViewAdapter;
import com.master.snapshotwizard.adapters.ListRecycleViewAdapter;
import com.master.snapshotwizard.adapters.ListRecycleViewAdapter.ItemClickListener;
import com.master.snapshotwizard.interfaces.ActivityWithSwitchHandler;
import com.master.snapshotwizard.models.ElementWrapper;
import com.master.snapshotwizard.models.ListItem;
import com.master.snapshotwizard.utils.Log;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Configuration implements ItemClickListener {
    private ListRecycleViewAdapter listRecycleViewAdapater;
    private ImagePickerRecycleViewAdapter imagePickerRecycleViewAdapter;
    private ActivityWithSwitchHandler currActivity;

    public void configureToolbar(AppCompatActivity activity, int subtitleId) {
        Log.d("ToolbarConfiguration", "configureToolbar");
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setSubtitle(activity.getResources().getString(subtitleId));
        activity.setSupportActionBar(toolbar);
    }

    public void configureList(ActivityWithSwitchHandler activity, String activityName) {
        Log.d("ListConfiguration", "starting configureList");
        this.currActivity = activity;
        switch (activityName) {
            case "Operations":
                this.configureOperations(this.currActivity);
                break;
            case "Save":
                this.configureSave(this.currActivity);
                break;
            case "MediaPicker":
                this.configureMediaPicker(this.currActivity);
                break;
            case "CompressResize":
                // configureMediaPicker(currActivity);
                break;
            default:
                Log.d("ListConfiguration", "switch EndedInDefault");
        }
        Log.d("ListConfiguration", "ending configureList");
    }


    private void configureOperations(ActivityWithSwitchHandler activity) {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(activity, R.string.operations_save_title), getText(activity, R.string.operations_save_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.operations_share_title), getText(activity, R.string.operations_share_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.operations_script_title), getText(activity, R.string.operations_script_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.operations_google_title), getText(activity, R.string.operations_google_desc)));

        RecyclerView recyclerView = activity.findViewById(R.id.operation_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.listRecycleViewAdapater = new ListRecycleViewAdapter(activity, listDataset);
        this.listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(this.listRecycleViewAdapater);
    }

    private void configureSave(ActivityWithSwitchHandler activity) {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(activity, R.string.save_save_title), getText(activity, R.string.save_save_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_compress_title), getText(activity, R.string.save_compress_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_share_title), getText(activity, R.string.save_share_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_script_title), getText(activity, R.string.save_script_desc)));
        listDataset.add(new ListItem(getText(activity, R.string.save_google_title), getText(activity, R.string.save_google_desc)));

        RecyclerView recyclerView = activity.findViewById(R.id.save_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.listRecycleViewAdapater = new ListRecycleViewAdapter(activity, listDataset);
        this.listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(this.listRecycleViewAdapater);
    }


    private void configureMediaPicker(ActivityWithSwitchHandler activity) {
        ArrayList<ElementWrapper> fileDataset = JavaScriptInterface.getSelectedElements();
        RecyclerView recyclerView = activity.findViewById(R.id.image_picker_recycle_view);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(activity, FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        flexboxLayoutManager.setMaxLine(5);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        this.imagePickerRecycleViewAdapter = new ImagePickerRecycleViewAdapter(activity, fileDataset);
        this.imagePickerRecycleViewAdapter.setClickListener(this);
        recyclerView.setAdapter(this.imagePickerRecycleViewAdapter);
    }

    private static String getText(AppCompatActivity activity, int id) {
        return activity.getResources().getString(id);
    }

    public ImagePickerRecycleViewAdapter getImagePickerRecycleViewAdapter() {
        return this.imagePickerRecycleViewAdapter;
    }

    @Override
    public void onItemClick(View view, int position) throws MalformedURLException {
        Log.d(this, "You clicked " + this.listRecycleViewAdapater.getItem(position) + " on row number " + position);
        this.currActivity.switchHandler(view, position);
    }
}
