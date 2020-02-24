package com.master.snapshotwizard.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.interfaces.ActivityWithSwitchHandler;
import com.master.snapshotwizard.utils.Log;

public class OperationActivity extends ActivityWithSwitchHandler {

    private static boolean firstRun = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(this);
        setContentView(R.layout.activity_operation);
        if(firstRun){
            firstRun = false;
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }
        /* Only for when WebPageRetriver is not used - TESTING */
        WebpageRetrieverActivity.URL = getResources().getString(R.string.main_url);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) {
        switch (position){
            case 0:
                /* Save */
                Log.d(this, "Swith 0");
                startActivity(new Intent(this, SaveActivity.class));
                break;
            case 1:
                /* Share via */
                Log.d(this, "Swith 1");
                break;
            case 2:
                /* Share via */
            case 3:
                /* Apply script */
            case 4:
                /* Search on Google */
            default:
                Log.d(this, "Swith default");
                break;
        }
    }
}
