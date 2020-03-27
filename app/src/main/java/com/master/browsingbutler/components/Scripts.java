package com.master.browsingbutler.components;

import android.app.Activity;

import com.master.browsingbutler.R;
import com.master.browsingbutler.models.Script;

import java.util.ArrayList;

public class Scripts {

    public static ArrayList<Script> allScripts;


    public static void initScripts(Activity activity) {
        allScripts = new ArrayList<>();
        /* Compress all elements to 50% */
        Script compressTo50 = new Script(activity.getString(R.string.compresTo50_title), activity.getString(R.string.compresTo50_desc), true);
        /* Download and send elements */
        Script downloadAndSend = new Script(activity.getString(R.string.downloadAndSend_title), activity.getString(R.string.downloadAndSend_desc), true);

        allScripts.add(compressTo50);
        allScripts.add(downloadAndSend);
    }
}
