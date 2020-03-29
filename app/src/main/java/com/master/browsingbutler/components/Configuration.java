package com.master.browsingbutler.components;

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
import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.ActivityWithSwitchHandler;
import com.master.browsingbutler.adapters.ImagePickerRecycleViewAdapter;
import com.master.browsingbutler.adapters.ListRecycleViewAdapter;
import com.master.browsingbutler.adapters.ListRecycleViewAdapter.ItemClickListener;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.models.ListItem;
import com.master.browsingbutler.models.Script;
import com.master.browsingbutler.models.ScriptItem;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Configuration implements ItemClickListener {
    private ActivityWithSwitchHandler currActivity;
    public static ArrayList<ListItem> premadeListDataset;
    public static ArrayList<ListItem> customListDataset;

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
                this.configureOperations();
                break;
            case "Save":
                this.configureSave();
                break;
            case "MediaPicker":
                this.configureMediaPicker();
                break;
            case "Scripts":
                this.configureScripts();
                break;
            default:
                Log.d("ListConfiguration", "switch EndedInDefault");
        }
        Log.d("ListConfiguration", "ending configureList");
    }


    private void configureOperations() {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(this.currActivity, R.string.operations_save_title), getText(this.currActivity, R.string.operations_save_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.operations_share_title), getText(this.currActivity, R.string.operations_share_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.operations_script_title), getText(this.currActivity, R.string.operations_script_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.operations_google_title), getText(this.currActivity, R.string.operations_google_desc)));

        RecyclerView recyclerView = this.currActivity.findViewById(R.id.operation_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.currActivity));
        ListRecycleViewAdapter listRecycleViewAdapater = new ListRecycleViewAdapter(this.currActivity, listDataset);
        listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(listRecycleViewAdapater);
    }

    private void configureSave() {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(this.currActivity, R.string.save_save_title), getText(this.currActivity, R.string.save_save_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.save_compress_title), getText(this.currActivity, R.string.save_compress_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.save_share_title), getText(this.currActivity, R.string.save_share_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.save_script_title), getText(this.currActivity, R.string.save_script_desc)));
        listDataset.add(new ListItem(getText(this.currActivity, R.string.save_google_title), getText(this.currActivity, R.string.save_google_desc)));

        RecyclerView recyclerView = this.currActivity.findViewById(R.id.save_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.currActivity));
        ListRecycleViewAdapter listRecycleViewAdapater = new ListRecycleViewAdapter(this.currActivity, listDataset);
        listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(listRecycleViewAdapater);
    }

    private void configureScripts() {
        premadeListDataset = new ArrayList<>();
        customListDataset = new ArrayList<>();

        /* Get the scripts array - fill the list dataset with info */
        Scripts.getAllScripts().forEach(script -> {
            ListItem listItem = new ScriptItem(script.getTitle(), script.getDescription(), script);
            if (script.isPremade()) {
                premadeListDataset.add(listItem);
            } else {
                customListDataset.add(listItem);
            }
        });
        this.createScriptRecyclerViews(Script.Type.PREMADE, premadeListDataset);
        this.createScriptRecyclerViews(Script.Type.CUSTOM, customListDataset);
    }

    private void createScriptRecyclerViews(Script.Type type, ArrayList<ListItem> dataset) {
        RecyclerView recyclerView = this.currActivity.findViewById(type == Script.Type.PREMADE ? R.id.premade_script_recycle_view : R.id.custom_script_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.currActivity));
        ListRecycleViewAdapter recyclerViewAdapter = new ListRecycleViewAdapter(this.currActivity, dataset);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    private void configureMediaPicker() {
        ArrayList<ElementWrapper> fileDataset = JavaScriptInterface.getSelectedElements();
        RecyclerView recyclerView = this.currActivity.findViewById(R.id.image_picker_recycle_view);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.currActivity, FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        flexboxLayoutManager.setMaxLine(5);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        ImagePickerRecycleViewAdapter imagePickerRecycleViewAdapter = new ImagePickerRecycleViewAdapter(this.currActivity, fileDataset);
        imagePickerRecycleViewAdapter.setClickListener(this);
        recyclerView.setAdapter(imagePickerRecycleViewAdapter);
    }

    private static String getText(AppCompatActivity activity, int id) {
        return activity.getResources().getString(id);
    }

    public ImagePickerRecycleViewAdapter getImagePickerRecycleViewAdapter() {
        RecyclerView recyclerView = this.currActivity.findViewById(R.id.image_picker_recycle_view);
        return (ImagePickerRecycleViewAdapter) recyclerView.getAdapter();
    }

    @Override
    public void onItemClick(View view, int position) throws MalformedURLException {
        Log.d(this, "You clicked " + view.toString() + " on row number " + position);
        this.currActivity.switchHandler(view, position);
    }
}
