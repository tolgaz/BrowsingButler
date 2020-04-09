package com.master.browsingbutler.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.master.browsingbutler.App;
import com.master.browsingbutler.activities.OperationActivity;

public class ActivityUtils {
    public static void engageActivityComplete(Activity activity, String text) {
        displayToast(text);
        returnBackToOperationScreen(activity);
    }

    public static void displayToast(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    private static void returnBackToOperationScreen(Activity activity) {
        Intent intent = new Intent(activity, OperationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static int dpToPx(float dp) {
        return (int) (dp * (((float) App.getResourses().getDisplayMetrics().densityDpi) / 160.0f));
    }
}


