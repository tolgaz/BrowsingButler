package com.master.browsingbutler.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.interfaces.ActivityWithSwitchHandler;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;

public class ScriptActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(this);
        this.setContentView(R.layout.activity_operation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_operation_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Operations");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.transitionBack(this);
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {
        //
    }
}
