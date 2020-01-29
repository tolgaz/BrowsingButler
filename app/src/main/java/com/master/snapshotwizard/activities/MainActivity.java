package com.master.snapshotwizard.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, WebpageRetrieverActivity.class));
    }
}
