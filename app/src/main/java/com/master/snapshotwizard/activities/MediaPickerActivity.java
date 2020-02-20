package com.master.snapshotwizard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.models.ElementWrapper;
import com.master.snapshotwizard.components.JavaScriptInterface;
import com.master.snapshotwizard.adapters.ImagePickerRecycleViewAdapter;
import com.master.snapshotwizard.adapters.ImagePickerRecycleViewAdapter.ImagePickerRecycleViewHolder;
import com.master.snapshotwizard.utils.Log;

import java.util.ArrayList;

public class MediaPickerActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_mediapicker);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Button selectAll = findViewById(R.id.select_all);
        selectAll.setOnClickListener(v -> {
            Log.d(this, "selectAll clicked");
            ImagePickerRecycleViewAdapter imagePickerRecycleViewAdapter = WebpageRetrieverActivity.configuration.getImagePickerRecycleViewAdapter();
            ArrayList<ElementWrapper> elementWrappers = imagePickerRecycleViewAdapter.getFileDataset();
            ArrayList<ImagePickerRecycleViewHolder> viewHolders = imagePickerRecycleViewAdapter.getViewHolders();
            viewHolders.forEach(ImagePickerRecycleViewHolder::select);
            elementWrappers.forEach(eW -> eW.setChosen(true));
        });

        Button continueCompress = findViewById(R.id.continue_compress);
        continueCompress.setOnClickListener(v -> startCompressResizeActivity());
   }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_media_picker_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "MediaPicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    public void switchHandler(View view, int position) {
        Log.d(this, "switchHandler");
        ElementWrapper clickedElementWrapper = JavaScriptInterface.getSelectedElements().get(position);
        Log.d(this, "item on pos: " + position + ", was: " + clickedElementWrapper.getChosen() + " - chosen, now is: " + !clickedElementWrapper.getChosen());
        clickedElementWrapper.setChosen(!clickedElementWrapper.getChosen());
        Log.d(this, "switchHandler done");
    }

    public void startCompressResizeActivity() {
        Log.d(this, "startCompressResizeActivity clicked");
        startActivity(new Intent(this, CompressResizeActivity.class));
    }

}
