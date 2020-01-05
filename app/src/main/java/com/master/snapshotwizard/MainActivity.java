package com.master.snapshotwizard;

import com.master.snapshotwizard.Log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, WebpageRetriever.class));
    }
}
