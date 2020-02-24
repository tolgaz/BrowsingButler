package com.master.snapshotwizard.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.activities.OperationActivity;

public class ActivityUtils {

    private Activity activity;

    public ActivityUtils(Activity activity){
        this.activity = activity;
    }

    public void engageActivityComplete(String text) {
        displayToastSuccessful(text);
        returnBackToOperationScreen();
    }

    private void displayToastSuccessful(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    private void returnBackToOperationScreen() {
        Intent intent = new Intent(activity, OperationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        transitionBack();
    }

    public void transitionBack() {
        activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

}
