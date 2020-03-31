package com.master.browsingbutler;

import android.app.Application;
import android.content.res.Resources;

import com.master.browsingbutler.utils.Log;

public class App extends Application {
    private static App instance;
    private static Resources resources;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(this, "im in app.jva oncreate");
        instance = this;
        resources = this.getResources();
    }

    public static App getInstance() {
        return instance;
    }

    public static Resources getResourses() {
        return resources;
    }
}