package com.master.browsingbutler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.adapters.ImagePickerRecycleViewAdapter;
import com.master.browsingbutler.adapters.ImagePickerRecycleViewAdapter.ImagePickerRecycleViewHolder;
import com.master.browsingbutler.components.JavaScriptInterface;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class MediaPickerActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_mediapicker);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_media_picker_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "MediaPicker");

        Button selectAll = this.findViewById(R.id.select_all);
        selectAll.setOnClickListener(v -> {
            Log.d(this, "selectAll clicked");
            ImagePickerRecycleViewAdapter imagePickerRecycleViewAdapter = WebpageRetrieverActivity.configuration.getImagePickerRecycleViewAdapter();
            List<ElementWrapper> elementWrappers = imagePickerRecycleViewAdapter.getFileDataset();
            ArrayList<ImagePickerRecycleViewHolder> viewHolders = imagePickerRecycleViewAdapter.getViewHolders();
            viewHolders.forEach(ImagePickerRecycleViewHolder::select);
            elementWrappers.forEach(eW -> eW.setChosen(true));
        });

        Button continueCompress = this.findViewById(R.id.continue_compress);
        continueCompress.setOnClickListener(v -> this.startCompressResizeActivity());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) {
        Log.d(this, "switchHandler");
        ElementWrapper clickedElementWrapper = JavaScriptInterface.getSelectedElements().get(position);
        Log.d(this, "item on pos: " + position + ", was: " + clickedElementWrapper.getChosen() + " - chosen, now is: " + !clickedElementWrapper.getChosen());
        clickedElementWrapper.setChosen(!clickedElementWrapper.getChosen());
        Log.d(this, "switchHandler done");
    }

    public void startCompressResizeActivity() {
        Log.d(this, "startCompressResizeActivity clicked");
        this.startActivity(new Intent(this, CompressResizeActivity.class));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
