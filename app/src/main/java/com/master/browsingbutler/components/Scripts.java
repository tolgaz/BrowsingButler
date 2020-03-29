package com.master.browsingbutler.components;

import android.content.Context;

import com.master.browsingbutler.R;
import com.master.browsingbutler.models.Script;

import java.util.ArrayList;
import java.util.List;

public class Scripts {

    private static List<Script> allScripts = null;

    public static void initScripts(Context context) {
        List<Script> allScripts = new ArrayList<>();
        /* Compress all elements to 50% */
        Script compressTo50 = new Script(context.getString(R.string.compresTo50_title), context.getString(R.string.compresTo50_desc), true);
        /* Download and send elements */
        Script downloadAndSend = new Script(context.getString(R.string.downloadAndSend_title), context.getString(R.string.downloadAndSend_desc), true);

        allScripts.add(compressTo50);
        allScripts.add(downloadAndSend);
        setAllScripts(allScripts);
    }

    public static List<Script> getAllScripts() {
        return allScripts;
    }

    public static void setAllScripts(List<Script> allScripts) {
        Scripts.allScripts = allScripts;
    }

    public static void addScript(Script script) {
        allScripts.add(script);
    }
}
