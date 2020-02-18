package com.master.snapshotwizard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.ElementGrabber;
import com.master.snapshotwizard.utils.Log;

import java.net.MalformedURLException;

public class SaveActivity extends ActivityWithSwitchHandler  {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_save);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) throws MalformedURLException {
        switch (position){
            case 0:
                /* Save */
                Log.d(this, "pos 0 ");
                ElementGrabber.grabElements();
                displayToastSuccessful();
                returnBackToOperationScreen();
                break;
            case 1:
                /* Save and compress */
                Log.d(this, "pos 1 ");
                ElementGrabber.grabElements();
                startActivity(new Intent(this, MediaPickerActivity.class));
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

    private void returnBackToOperationScreen() {
        finish();
        transitionBack();
    }

    private void transitionBack() {
        overridePendingTransition( R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        transitionBack();
    }

    private void displayToastSuccessful() {
        Toast.makeText(this, "Media has been successfully saved!", Toast.LENGTH_LONG).show();
    }
}
