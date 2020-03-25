package com.master.browsingbutler.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.OperationActivity;

public class ActivityUtils {

    private Activity activity;

    public ActivityUtils(Activity activity) {
        this.activity = activity;
    }

    public void engageActivityComplete(String text) {
        this.displayToastSuccessful(text);
        this.returnBackToOperationScreen();
    }

    private void displayToastSuccessful(String text) {
        Toast.makeText(this.activity, text, Toast.LENGTH_LONG).show();
    }

    private void returnBackToOperationScreen() {
        Intent intent = new Intent(this.activity, OperationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.activity.startActivity(intent);
        this.transitionBack();
    }

    public void transitionBack() {
        this.activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

}
