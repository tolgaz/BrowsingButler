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
import com.master.browsingbutler.models.scripts.ScriptItem;
import com.master.browsingbutler.utils.Log;

public class ScriptActivity extends ActivityWithSwitchHandler {

    private boolean isExecution = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_script_overview);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Button newScript = this.findViewById(R.id.new_script_button);
        if (this.getIntent().getBooleanExtra("EXECUTION", false)) {
            this.isExecution = true;
            LinearLayout linearLayout = this.findViewById(R.id.new_script_button_layout);
            linearLayout.setVisibility(View.GONE);
        } else {
            this.isExecution = false;
            newScript.setOnClickListener(v -> this.startActivity(this.configureScriptOptionIntent(v, -1, true)));
        }

        WebpageRetrieverActivity.configuration.configureToolbar(this, this.isExecution ? R.string.toolbar_script_exec_screen : R.string.toolbar_script_screen);
        WebpageRetrieverActivity.configuration.configureList(this, "Scripts");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void switchHandler(View view, int position) {
        if (this.isExecution) {
            this.startScriptExecution(view, position);
        } else {
            this.startActivity(this.configureScriptOptionIntent(view, position, false));
        }
    }

    private void startScriptExecution(View view, int position) {
        boolean premade = ((View) view.getParent().getParent().getParent()).getId() == R.id.premade_script_layout;
        ScriptItem script = (ScriptItem) (premade ? Configuration.premadeListDataset.get(position) : Configuration.customListDataset.get(position));
        script.getScript().startExecution(this);
    }

    private Intent configureScriptOptionIntent(View view, int position, boolean newScript) {
        Intent intent = new Intent(this, ScriptOptionsActivity.class);
        if (!newScript) {
            boolean premade = ((View) view.getParent().getParent().getParent()).getId() == R.id.premade_script_layout;
            ScriptItem script = (ScriptItem) (premade ? Configuration.premadeListDataset.get(position) : Configuration.customListDataset.get(position));
            intent.putExtra("SCRIPT-ID", script.getScript().getID());
        }
        intent.putExtra("NEW_SCRIPT", newScript);
        return intent;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
