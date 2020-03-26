package com.master.browsingbutler.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.OperationActivity;

public class ActivityUtils {
    public static void engageActivityComplete(Activity activity, String text) {
        displayToastSuccessful(activity, text);
        returnBackToOperationScreen(activity);
    }

    private static void displayToastSuccessful(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    private static void returnBackToOperationScreen(Activity activity) {
        Intent intent = new Intent(activity, OperationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        transitionBack(activity);
    }

    public static void transitionBack(Activity activity) {
        activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
