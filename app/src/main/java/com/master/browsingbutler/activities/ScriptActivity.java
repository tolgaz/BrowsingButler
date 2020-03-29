package com.master.browsingbutler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.Configuration;
import com.master.browsingbutler.models.ScriptItem;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

public class ScriptActivity extends ActivityWithSwitchHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_script_overview);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Button newScript = this.findViewById(R.id.new_script_button);
        newScript.setOnClickListener(v -> this.startActivity(new Intent(this, ScriptOptionsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_script_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Scripts");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.transitionBack(this);
    }

    @Override
    public void switchHandler(View view, int position) {
        boolean premade = ((LinearLayout) view.getParent().getParent()).getId() == R.id.premade_script_layout;
        ScriptItem script = (ScriptItem) (premade ? Configuration.premadeListDataset.get(position) : Configuration.customListDataset.get(position));

        Intent intent = new Intent(this, ScriptOptionsActivity.class);
        intent.putExtra("SCRIPT-ID", script.getScript().getID());
        this.startActivity(intent);
    }
}
