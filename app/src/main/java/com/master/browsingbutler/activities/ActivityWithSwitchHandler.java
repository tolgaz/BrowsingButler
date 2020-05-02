package com.master.browsingbutler.activities;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

public abstract class ActivityWithSwitchHandler extends AppCompatActivity {
    public abstract void switchHandler(View view, int position);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", "Option clicked!");
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_script_settings:
                Log.d("onOptionsItemSelected", "script!");
                Intent intent = new Intent(this, ScriptActivity.class);
                intent.putExtra("EXECUTION", false);
                this.startActivity(intent);
                return true;
            default:
                Log.d("onOptionsItemSelected", "DEFAULT: " + item.getItemId());
                return super.onOptionsItemSelected(item);
        }
    }
}
