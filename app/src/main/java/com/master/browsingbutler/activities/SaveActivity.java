package com.master.browsingbutler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.ShareViaOpenWithHandler;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.ElementGrabber;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;

public class SaveActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_save);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_save_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Save");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    
    public void downloadAllElements() throws MalformedURLException {
        /* Save */
        Log.d(this, "pos 0 ");
        ElementGrabber.grabElements();
        ActivityUtils.engageActivityComplete(this, "Media has been successfully saved!");
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {
        switch (position) {
            case 0:
                /* Save */
                downloadAllElements();
                break;
            case 1:
                /* Save and compress */
                Log.d(this, "pos 1 ");
                ElementGrabber.grabElements();
                this.startActivity(new Intent(this, MediaPickerActivity.class));
                break;
            case 2:
                /* Save and share */
                Log.d(this, "pos 2 ");
                ElementGrabber.grabElements();
                ShareViaOpenWithHandler.saveAndShare(this);
                break;
            case 3:
                /* Save and apply script */
                Log.d(this, "pos 3 ");
                break;
            case 4:
                /* Save and search on google */
                Log.d(this, "pos 4 ");
                break;
            default:
                Log.d(this, "Swith default");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.transitionBack(this);
    }
}
