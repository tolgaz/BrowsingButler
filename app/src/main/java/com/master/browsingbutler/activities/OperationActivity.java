package com.master.browsingbutler.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.ShareViaOpenWithHandler;
import com.master.browsingbutler.utils.Log;

public class OperationActivity extends ActivityWithSwitchHandler {

    private static boolean firstRun = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_operation);
        if (firstRun) {
            firstRun = false;
            this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }
        /* Only for when WebPageRetriver is not used - TESTING */
        WebpageRetrieverActivity.URL = this.getResources().getString(R.string.main_url);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
    public void switchHandler(View view, int position) {
        switch (position) {
            case 0:
                /* Save */
                Log.d(this, "Swith 0");
                this.startActivity(new Intent(this, SaveActivity.class));
                break;
            case 1:
                /* Share via */
                Log.d(this, "Swith 1");
                ShareViaOpenWithHandler.share(this, false);
                break;
            case 2:
                /* Apply script */
                Log.d(this, "Swith 2 - apply script");
                Intent intent = new Intent(this, ScriptActivity.class);
                intent.putExtra("EXECUTION", true);
                this.startActivity(intent);
            case 3:
                /* Search on Google */
            default:
                Log.d(this, "Swith default");
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}
