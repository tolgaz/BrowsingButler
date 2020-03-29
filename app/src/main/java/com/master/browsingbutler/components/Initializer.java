package com.master.browsingbutler.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.Script;

import java.lang.reflect.Type;
import java.util.List;

public class Initializer {

    /* DEV TOOL */
    private static boolean overWriteSharedPref = false;

    public static void initApplication(Context context) {
        /* If scripts doesn't exist - create and save them */
        /* or retrieve them */
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPref(context);
        String json = sharedPreferences.getString(context.getString(R.string.scripts_save_key), null);

        /* only tempoararry this shoudl bne in the activity first launched */
        if (overWriteSharedPref || json == null) {
            Scripts.initScripts(context);
            saveScripts(context);
        } else {
            Type listOfScriptsType = new TypeToken<List<Script>>() {
            }.getType();
            List<Script> scripts = gson.fromJson(json, listOfScriptsType);
            Scripts.setAllScripts(scripts);
        }
    }

    public static void saveScripts(Context context) {
        SharedPreferences sharedPreferences = getSharedPref(context);
        Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Scripts.getAllScripts());
        prefsEditor.putString(context.getString(R.string.scripts_save_key), json);
        prefsEditor.apply();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(context.getString(R.string.application_shared_pref_name), Context.MODE_PRIVATE);
    }
}
