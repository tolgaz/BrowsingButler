package com.master.browsingbutler.activities;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

import java.net.MalformedURLException;

public abstract class ActivityWithSwitchHandler extends AppCompatActivity {
    public abstract void switchHandler(View view, int position) throws MalformedURLException;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", "Option clicked!");
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_options:
                Log.d("onOptionsItemSelected", "options!");
                return true;
            case R.id.menu_script_settings:
                Log.d("onOptionsItemSelected", "script!");
                this.startActivity(new Intent(this, ScriptActivity.class));
                return true;
            case R.id.menu_FAQ:
                Log.d("onOptionsItemSelected", "faq!");
                return true;
            default:
                Log.d("onOptionsItemSelected", "DEFAULT: " + item.getItemId());
                return super.onOptionsItemSelected(item);
        }
    }
}
