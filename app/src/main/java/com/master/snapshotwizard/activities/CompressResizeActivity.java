package com.master.snapshotwizard.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.components.ElementWrapper;
import com.master.snapshotwizard.components.JavaScriptInterface;
import com.master.snapshotwizard.utils.Log;

public class CompressResizeActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_compressresize);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
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
}
