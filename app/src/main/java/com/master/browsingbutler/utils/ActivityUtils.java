package com.master.browsingbutler.utils;

import android.content.Intent;
import android.widget.Toast;

import com.master.browsingbutler.App;
import com.master.browsingbutler.activities.OperationActivity;

public class ActivityUtils {
    public static void engageActivityComplete(String text) {
        displayToastSuccessful(text);
        returnBackToOperationScreen();
    }

    public static void displayToastSuccessful(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    private static void returnBackToOperationScreen() {
        Intent intent = new Intent(App.getInstance(), OperationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        App.getInstance().startActivity(intent);
    }

    public static int dpToPx(float dp) {
        return (int) (dp * (((float) App.getResourses().getDisplayMetrics().densityDpi) / 160.0f));
    }
}


