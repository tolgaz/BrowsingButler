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
import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.ActivityWithSwitchHandler;
import com.master.browsingbutler.adapters.ImagePickerRecycleViewAdapter;
import com.master.browsingbutler.adapters.ListRecycleViewAdapter;
import com.master.browsingbutler.adapters.ListRecycleViewAdapter.ItemClickListener;
import com.master.browsingbutler.adapters.SearchQueryRecycleViewAdapter;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.models.ListItem;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptItem;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Configuration implements ItemClickListener {
    private static final String TAG = "Configuration";
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
            case "SearchQuery":
                this.configureSearchQuery();
                break;
            default:
                Log.d("ListConfiguration", "switch EndedInDefault");
        }
        Log.d("ListConfiguration", "ending configureList");
    }

    private void configureSearchQuery() {
        Log.d(TAG, "configureSearchQuery");
        List<ElementWrapper> dataset = JavaScriptInterface.getSelectedElements().stream().filter(ElementWrapper::isText).collect(Collectors.toList());
        // dataset.forEach(elementWrapper -> elementWrapper.setChosen(false));

        RecyclerView recyclerView = this.currActivity.findViewById(R.id.search_query_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.currActivity));
        SearchQueryRecycleViewAdapter searchQueryRecycleViewAdapter = new SearchQueryRecycleViewAdapter(this.currActivity, dataset);
        searchQueryRecycleViewAdapter.setClickListener(this);
        recyclerView.setAdapter(searchQueryRecycleViewAdapter);
    }


    private void configureOperations() {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(R.string.operations_save_title), getText(R.string.operations_save_desc)));
        listDataset.add(new ListItem(getText(R.string.operations_share_title), getText(R.string.operations_share_desc)));
        listDataset.add(new ListItem(getText(R.string.operations_script_title), getText(R.string.operations_script_desc)));
        listDataset.add(new ListItem(getText(R.string.operations_google_title), getText(R.string.operations_google_desc)));

        RecyclerView recyclerView = this.currActivity.findViewById(R.id.operation_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.currActivity));
        ListRecycleViewAdapter listRecycleViewAdapater = new ListRecycleViewAdapter(this.currActivity, listDataset);
        listRecycleViewAdapater.setClickListener(this);
        recyclerView.setAdapter(listRecycleViewAdapater);
    }

    private void configureSave() {
        ArrayList<ListItem> listDataset = new ArrayList<>();
        listDataset.add(new ListItem(getText(R.string.save_save_title), getText(R.string.save_save_desc)));
        listDataset.add(new ListItem(getText(R.string.save_compress_title), getText(R.string.save_compress_desc)));
        listDataset.add(new ListItem(getText(R.string.save_share_title), getText(R.string.save_share_desc)));
        //listDataset.add(new ListItem(getText(R.string.save_script_title), getText(R.string.save_script_desc)));
        listDataset.add(new ListItem(getText(R.string.save_google_title), getText(R.string.save_google_desc)));

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
        List<ElementWrapper> fileDataset = JavaScriptInterface.getSelectedElements().stream().filter(ElementWrapper::isNotText).collect(Collectors.toList());
        fileDataset.forEach(elementWrapper -> elementWrapper.setChosen(false));
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

    private static String getText(int id) {
        return App.getResourses().getString(id);
    }

    public ImagePickerRecycleViewAdapter getImagePickerRecycleViewAdapter() {
        RecyclerView recyclerView = this.currActivity.findViewById(R.id.image_picker_recycle_view);
        return (ImagePickerRecycleViewAdapter) recyclerView.getAdapter();
    }

    public SearchQueryRecycleViewAdapter getSearchQueryRecycleViewAdapater() {
        RecyclerView recyclerView = this.currActivity.findViewById(R.id.search_query_recycle_view);
        return (SearchQueryRecycleViewAdapter) recyclerView.getAdapter();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(this, "You clicked " + view.toString() + " on row number " + position);
        this.currActivity.switchHandler(view, position);
    }
}
