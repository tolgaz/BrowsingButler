package com.master.browsingbutler.components;

import android.content.Context;

import com.master.browsingbutler.R;
import com.master.browsingbutler.models.Script;

import java.util.ArrayList;
import java.util.List;

public class Scripts {

    private static List<Script> allScripts = new ArrayList<>();

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

    public static void deleteScript(Script script) {
        /* Delete from list and update indices */
        int IDOfScriptToDelete = script.getID();
        List<Script> scripts = Scripts.getAllScripts();

        if (IDOfScriptToDelete != scripts.size() - 1) {
            for (int i = IDOfScriptToDelete + 1; i < scripts.size(); i++) {
                /* for each we want to decrement counter */
                Script currScript = scripts.get(i);
                int oldID = currScript.getID();
                currScript.setID(oldID - 1);
            }
        }
        scripts.remove(IDOfScriptToDelete);
        assert Scripts.getAllScripts().size() == Scripts.getAllScripts().get(Scripts.getAllScripts().size() - 1).getID();
    }
}
