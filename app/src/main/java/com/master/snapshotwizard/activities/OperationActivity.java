package com.master.snapshotwizard.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.master.snapshotwizard.R;
import com.master.snapshotwizard.utils.Log;

public class OperationActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        setContentView(R.layout.activity_operation);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}
