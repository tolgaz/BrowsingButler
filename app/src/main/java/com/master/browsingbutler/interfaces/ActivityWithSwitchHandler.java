package com.master.browsingbutler.interfaces;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;

public abstract class ActivityWithSwitchHandler extends AppCompatActivity {
    public abstract void switchHandler(View view, int position) throws MalformedURLException;
}
