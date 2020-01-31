package com.master.snapshotwizard.activities;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityWithSwitchHandler extends AppCompatActivity {
    public abstract void switchHandler(View view, int position);
}
