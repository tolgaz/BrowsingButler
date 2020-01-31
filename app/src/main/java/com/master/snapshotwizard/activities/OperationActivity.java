package com.master.snapshotwizard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.Log;

public class OperationActivity extends ActivityWithSwitchHandler {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_operation);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
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
