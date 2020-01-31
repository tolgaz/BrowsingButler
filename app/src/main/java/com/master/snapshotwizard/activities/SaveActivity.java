package com.master.snapshotwizard.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.ElementGrabber;
import com.master.snapshotwizard.utils.Log;

public class SaveActivity extends ActivityWithSwitchHandler  {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_save);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_save_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Save");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position){
        switch (position){
            case 0:
                /* Save */
                Log.d(this, "pos 0 ");
                ElementGrabber.grabElements();
                break;
            case 1:
                /* Save and compress */
                Log.d(this, "pos 1 ");
                break;
            case 2:
                /* Save and share */
                Log.d(this, "pos 2 ");
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
}
