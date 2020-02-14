package com.master.snapshotwizard.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.ElementGrabber;
import com.master.snapshotwizard.utils.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

public class CompressResizeActivity extends ActivityWithSwitchHandler {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_compressresize);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        ArrayList<File> savedFiles = ElementGrabber.getSavedFiles();
        URI url = savedFiles.get(0).toURI();
        /* TODO: Add layout for recycle.  Add some kind of overlay when clicked */

   }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_compressresize_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "CompressResize");
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {

    }
}
