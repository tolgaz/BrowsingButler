package com.master.browsingbutler.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.Initializer;
import com.master.browsingbutler.components.MultiSpinner;
import com.master.browsingbutler.components.MultiSpinner.MultiSpinnerListener;
import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.models.Script;
import com.master.browsingbutler.models.ScriptAction;
import com.master.browsingbutler.models.ScriptOption;
import com.master.browsingbutler.models.ScriptSelection;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptOptionsActivity extends ActivityWithSwitchHandler implements MultiSpinnerListener {

    Script script;
    boolean newScript;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this);
        this.setContentView(R.layout.activity_script_settings);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        WebpageRetrieverActivity.configuration.configureToolbar(this, R.string.toolbar_script_screen);

        if (this.getIntent().getExtras() != null) {
            this.newScript = (boolean) this.getIntent().getExtras().get("NEW_SCRIPT");
            if (!this.newScript) {
                int scriptID = (int) this.getIntent().getExtras().get("SCRIPT-ID");
                this.script = Scripts.getAllScripts().get(scriptID);
            } else {
                this.script = new Script();
            }
        }

        Log.d("ScriptOptionsActivity", this.script.toString());
        EditText title = this.findViewById(R.id.input_script_name);
        EditText desc = this.findViewById(R.id.input_script_desc);
        this.fillAllScriptFields(title, desc);

        if (ScriptAction.getScriptActions() == null) this.initScriptActions();
        if (ScriptSelection.getScriptSelections() == null) this.initScriptSelections();

        /* text view config */
        TextView scriptActions = this.findViewById(R.id.script_actions);
        TextView scriptSelections = this.findViewById(R.id.script_selections);
        this.configureOptionTextView(this.script.getActions(), scriptActions);
        this.configureOptionTextView(this.script.getSelections(), scriptSelections);

        /* createScriptButton button */
        Button createScriptButton = this.findViewById(R.id.create_script_button);

        /* spinner actions and selection config */
        MultiSpinner<ScriptAction> actionSpinner = this.findViewById(R.id.script_action_spinner);
        actionSpinner.setItems(ScriptAction.getScriptActions(), this.script.getActions(), Script.Option.ACTION, this.getString(R.string.action_spinner_text), this);

        MultiSpinner<ScriptSelection> selectionSpinner = this.findViewById(R.id.script_selection_spinner);
        selectionSpinner.setItems(ScriptSelection.getScriptSelections(), this.script.getSelections(), Script.Option.SELECTION, this.getString(R.string.selection_spinner_text), this);

        ImageButton scriptActionButton = this.findViewById(R.id.script_action_button);
        ImageButton scriptSelectionButton = this.findViewById(R.id.script_selection_button);

        if (this.script.isPremade()) {
            scriptActionButton.setVisibility(View.GONE);
            scriptSelectionButton.setVisibility(View.GONE);
            createScriptButton.setVisibility(View.GONE);
        } else {
            if (!this.newScript) createScriptButton.setText("Save changes");
            scriptActionButton.setOnClickListener(v -> actionSpinner.performClick());
            scriptSelectionButton.setOnClickListener(v -> selectionSpinner.performClick());
            createScriptButton.setOnClickListener(v -> {
                /* validate input */
                if (!this.validateInput(title, scriptActions, scriptSelections)) return;
                if (this.newScript) Scripts.addScript(this.script);
                this.onBackPressed();
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass(), "onPause!!");
        /* Save new script info */
        Initializer.saveScripts(this.getApplicationContext());
    }

    private boolean validateInput(EditText title, TextView scriptActions, TextView scriptSelections) {
        if (title.getText().toString().isEmpty()) {
            title.setError("You must give the script a name!");
            return false;
        }
        if (scriptActions.getText().toString().isEmpty()) {
            scriptActions.requestFocus();
            scriptActions.setError("You must choose some action(s) for the script!");
            return false;
        }
        if (scriptSelections.getText().toString().isEmpty()) {
            scriptSelections.requestFocus();
            scriptSelections.setError("You must choose some selection(s) for the script!");
            return false;
        }
        return true;
    }

    private <T extends ScriptOption> void configureOptionTextView(List<T> options, TextView textView) {
        if (options.isEmpty()) {
            textView.setText(null);
        } else {
            textView.setError(null);
            textView.setTextColor(Color.BLACK);
            StringBuilder stringListOfOptions = new StringBuilder();
            options.forEach(option -> stringListOfOptions.append(option.getTitle()).append(". "));
            textView.setText(stringListOfOptions);
            textView.setMaxLines(3);
        }

        if (this.script.isPremade()) {
            textView.setInputType(EditorInfo.TYPE_NULL);
            textView.setEnabled(false);
        }
    }


    private void fillAllScriptFields(EditText title, EditText desc) {
        /* Name and desc */
        title.setText(this.script.getTitle());
        desc.setText(this.script.getDescription());

        if (this.script.isPremade()) {
            title.setInputType(EditorInfo.TYPE_NULL);
            title.setEnabled(false);

            desc.setInputType(EditorInfo.TYPE_NULL);
            desc.setEnabled(false);

            return;
        }
        this.createAndAddTextWatchers(title, desc);
    }

    private void createAndAddTextWatchers(TextView title, TextView desc) {
        TextWatcher titleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ScriptOptionsActivity.this.script.setTitle(s.toString());
            }
        };

        TextWatcher descriptionTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ScriptOptionsActivity.this.script.setDescription(s.toString());
            }
        };

        title.addTextChangedListener(titleTextWatcher);
        desc.addTextChangedListener(descriptionTextWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this, "onResume");
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
    public void onItemsSelected(boolean[] selected, Script.Option optionType) {
        Log.d("spinner onitemselectd", Arrays.toString(selected));
        /* ugly */
        if (optionType == Script.Option.ACTION) {
            ArrayList<ScriptAction> actuallySelectedOptions = new ArrayList<>();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i])
                    actuallySelectedOptions.add(ScriptAction.getScriptActions().get(i));
            }

            this.script.setActions(actuallySelectedOptions);
            this.configureOptionTextView(this.script.getActions(), this.findViewById(R.id.script_actions));
        } else {
            ArrayList<ScriptSelection> actuallySelectedOptions = new ArrayList<>();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i])
                    actuallySelectedOptions.add(ScriptSelection.getScriptSelections().get(i));
            }

            this.script.setSelections(actuallySelectedOptions);
            this.configureOptionTextView(this.script.getSelections(), this.findViewById(R.id.script_selections));
        }
    }

    private void initScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_download_title), this.getString(R.string.script_action_download_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_compress_title), this.getString(R.string.script_action_compress_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_file_creator_title), this.getString(R.string.script_action_file_creator_desc)));
        scriptActions.add(new ScriptAction(this.getString(R.string.script_action_share_title), this.getString(R.string.script_action_share_desc)));
        ScriptAction.setScriptActions(scriptActions);
    }

    private void initScriptSelections() {
        ArrayList<ScriptSelection> scriptSelections = new ArrayList<>();
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_first_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_last_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_first_x_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_last_x_elements_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_pictures_title)));
        scriptSelections.add(new ScriptSelection(this.getString(R.string.script_selection_all_text_title)));
        ScriptSelection.setScriptSelections(scriptSelections);
    }

    @Override
    public void switchHandler(View view, int position) {
        throw new UnsupportedOperationException();
    }
}
