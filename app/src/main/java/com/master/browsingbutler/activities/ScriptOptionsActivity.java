package com.master.browsingbutler.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.master.browsingbutler.R;
import com.master.browsingbutler.components.Initializer;
import com.master.browsingbutler.components.MultiSpinner;
import com.master.browsingbutler.components.MultiSpinner.MultiSpinnerListener;
import com.master.browsingbutler.components.Scripts;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.actions.ActionCompress;
import com.master.browsingbutler.models.scripts.actions.ScriptAction;
import com.master.browsingbutler.models.scripts.interfaces.ScriptOption;
import com.master.browsingbutler.models.scripts.selections.ScriptSelection;
import com.master.browsingbutler.utils.ActivityUtils;
import com.master.browsingbutler.utils.Log;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScriptOptionsActivity extends ActivityWithSwitchHandler implements MultiSpinnerListener {

    Script script;
    boolean newScript;
    boolean hasChangeBeenMade = false;
    private boolean hasCancelButtonBeenClicked = false;
    private ActionCompress actionCompress = null;

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

        /* text view config */
        TextView scriptActions = this.findViewById(R.id.script_actions);
        TextView scriptSelections = this.findViewById(R.id.script_selections);
        this.configureOptionTextView(this.script.getActions(), scriptActions);
        /* this also sets action is available */
        this.setScriptResizeLayoutVisibility();
        this.configureOptionTextView(this.script.getSelections(), scriptSelections);

        /* Fill action fields */
        this.fillAllActionFields();

        /* cancel button */
        ImageButton cancelScriptButton = this.findViewById(R.id.cancel_script_button);
        cancelScriptButton.setOnClickListener(v -> {
            this.hasCancelButtonBeenClicked = true;
            this.onBackPressed();
        });

        /* spinner actions and selection config */
        MultiSpinner<ScriptAction> actionSpinner = this.findViewById(R.id.script_action_spinner);
        actionSpinner.setItems(ScriptAction.getScriptActions(), this.script.getActions(), Script.Option.ACTION, this.getString(R.string.action_spinner_text), this);

        MultiSpinner<ScriptSelection> selectionSpinner = this.findViewById(R.id.script_selection_spinner);
        selectionSpinner.setItems(ScriptSelection.getScriptSelections(), this.script.getSelections(), Script.Option.SELECTION, this.getString(R.string.selection_spinner_text), this);

        /* resize chekcbox */
        CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
        resizeCheckbox.setOnClickListener(v -> {
            this.hasChangeBeenMade = true;
            CompressResizeActivity.flipVisibilityResizeContainer(this);
        });

        if (this.script.isPremade()) {
            this.disableAllInputFields();
        } else {
            ImageButton scriptActionButton = this.findViewById(R.id.script_action_button);
            ImageButton scriptSelectionButton = this.findViewById(R.id.script_selection_button);
            Button createScriptButton = this.findViewById(R.id.create_script_button);
            Button deleteScriptButton = this.findViewById(R.id.delete_script_button);
            IndicatorSeekBar indicatorSeekBar = this.findViewById(R.id.seekBar);
            EditText widthInput = this.findViewById(R.id.resize_width_input);
            EditText heightInput = this.findViewById(R.id.resize_height_input);

            if (!this.newScript) {
                createScriptButton.setText("Save changes");
                deleteScriptButton.setOnClickListener(v -> {
                    Scripts.deleteScript(this.script);
                    //toast script deleted
                    ActivityUtils.displayToast(this.getString(R.string.toast_script_deleted));
                    this.onBackPressed();
                });
            } else {
                deleteScriptButton.setVisibility(View.GONE);
            }

            scriptActionButton.setOnClickListener(v -> actionSpinner.performClick());
            scriptSelectionButton.setOnClickListener(v -> selectionSpinner.performClick());
            createScriptButton.setOnClickListener(v -> this.onBackPressed());
            this.createAndSetSeekChangeListener(indicatorSeekBar);
            this.createAndAddTextWatcher(widthInput, "WIDTH");
            this.createAndAddTextWatcher(heightInput, "HEIGHT");
        }
    }

    private void createAndSetSeekChangeListener(IndicatorSeekBar indicatorSeekBar) {
        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                ScriptOptionsActivity.this.hasChangeBeenMade = true;
                ScriptOptionsActivity.this.actionCompress.setQuality(seekBar.getProgress());
            }
        });
    }

    private void disableAllInputFields() {
        ImageButton scriptActionButton = this.findViewById(R.id.script_action_button);
        ImageButton scriptSelectionButton = this.findViewById(R.id.script_selection_button);
        Button createScriptButton = this.findViewById(R.id.create_script_button);
        Button deleteScriptButton = this.findViewById(R.id.delete_script_button);

        scriptActionButton.setVisibility(View.GONE);
        scriptSelectionButton.setVisibility(View.GONE);
        createScriptButton.setVisibility(View.GONE);
        deleteScriptButton.setVisibility(View.GONE);

        if (this.actionCompress != null) {
            CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
            /* compress action settings */
            IndicatorSeekBar indicatorSeekBar = this.findViewById(R.id.seekBar);
            indicatorSeekBar.setUserSeekAble(false);
            resizeCheckbox.setEnabled(false);
        }
    }

    private void fillAllActionFields() {
        if (this.actionCompress != null) {
            /* compress action settings */
            IndicatorSeekBar indicatorSeekBar = this.findViewById(R.id.seekBar);
            indicatorSeekBar.setProgress(this.actionCompress.getQuality());

            if (this.actionCompress.getWidth() > 0 && this.actionCompress.getHeight() > 0) {
                CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
                resizeCheckbox.setChecked(true);
                ConstraintLayout resizeContainer = this.findViewById(R.id.container_resize);
                resizeContainer.setVisibility(View.VISIBLE);
                EditText widthInput = this.findViewById(R.id.resize_width_input);
                EditText heightInput = this.findViewById(R.id.resize_height_input);

                widthInput.setText(String.valueOf(this.actionCompress.getWidth()));
                heightInput.setText(String.valueOf(this.actionCompress.getHeight()));
            }
        }
    }


    private boolean validateInput() {
        boolean validationSuccessful = true;
        TextView scriptSelections = this.findViewById(R.id.script_selections);
        if (scriptSelections.getText().toString().isEmpty()) {
            scriptSelections.requestFocus();
            scriptSelections.setError("You must choose some selection(s) for the script!");
            validationSuccessful = false;
        }

        TextView scriptActions = this.findViewById(R.id.script_actions);
        if (scriptActions.getText().toString().isEmpty()) {
            scriptActions.requestFocus();
            scriptActions.setError("You must choose some action(s) for the script!");
            validationSuccessful = false;
        }

        EditText title = this.findViewById(R.id.input_script_name);
        if (title.getText().toString().isEmpty()) {
            title.requestFocus();
            title.setError("You must give the script a name!");
            validationSuccessful = false;
        }

        if (this.actionCompress != null) {
            /* Don't have to validate seekBar */
            CheckBox resizeCheckbox = this.findViewById(R.id.resize_chooser_checkbox);
            if (resizeCheckbox.isChecked()) {
                EditText heightInput = this.findViewById(R.id.resize_height_input);
                if (heightInput.getText().toString().isEmpty()) {
                    heightInput.requestFocus();
                    heightInput.setError("You must give a value for height!");
                    validationSuccessful = false;
                }
                EditText widthInput = this.findViewById(R.id.resize_width_input);
                if (widthInput.getText().toString().isEmpty()) {
                    widthInput.requestFocus();
                    widthInput.setError("You must give a value for width!");
                    validationSuccessful = false;
                }
            }
        }

        return validationSuccessful;
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
        this.createAndAddTextWatcher(title, "TITLE");
        this.createAndAddTextWatcher(desc, "DESCRIPTION");
    }

    private void createAndAddTextWatcher(TextView textView, String type) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ScriptOptionsActivity.this.hasChangeBeenMade = true;
                switch (type) {
                    case "TITLE":
                        ScriptOptionsActivity.this.script.setTitle(s.toString());
                        break;
                    case "DESCRIPTION":
                        ScriptOptionsActivity.this.script.setDescription(s.toString());
                        break;
                    case "WIDTH":
                        if (s.toString().isEmpty()) {
                            ScriptOptionsActivity.this.actionCompress.setWidth(0);
                        } else {
                            ScriptOptionsActivity.this.actionCompress.setWidth(Integer.parseInt(s.toString()));
                        }
                        break;
                    case "HEIGHT":
                        if (s.toString().isEmpty()) {
                            ScriptOptionsActivity.this.actionCompress.setHeight(0);
                        } else {
                            ScriptOptionsActivity.this.actionCompress.setHeight(Integer.parseInt(s.toString()));
                        }
                        break;
                }
            }
        };
        textView.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!this.hasCancelButtonBeenClicked && this.hasChangeBeenMade) {
            if (!this.validateInput()) return;
            Log.d("ScriptOptionsActivity", this.script.toString());
            if (this.newScript) {
                Scripts.addScript(this.script);
                ActivityUtils.displayToast(this.getString(R.string.toast_script_created));
            } else {
                ActivityUtils.displayToast(this.getString(R.string.toast_script_saved));
            }
        } else {
            if (!this.script.isPremade()) ActivityUtils.displayToast("No changes made!");
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass(), "onPause!!");
        /* Save new script info */
        if (!this.hasCancelButtonBeenClicked || this.hasChangeBeenMade) Initializer.saveScripts();
    }

    @Override
    public void onItemsSelected(boolean[] selected, Script.Option optionType, int[] order) {
        Log.d("spinner onitemselectd", Arrays.toString(selected));
        this.hasChangeBeenMade = true;
        /* ugly */
        if (optionType == Script.Option.ACTION) {
            ArrayList<ScriptAction> actuallySelectedOptions = new ArrayList<>();
            this.populateList(actuallySelectedOptions, selected.length);
            for (int i = 0; i < selected.length; i++) {
                ScriptAction currentScriptAction = ScriptAction.getScriptActions().get(i);
                if (selected[i]) {
                    ScriptAction clonedAction = (ScriptAction) currentScriptAction.clone(currentScriptAction);
//                    actuallySelectedOptions.add((ScriptAction) currentScriptAction.clone(currentScriptAction, actuallySelectedOptions.size()));
                    actuallySelectedOptions.add(order[i], clonedAction);
                }
            }
            actuallySelectedOptions.removeIf(Objects::isNull);
            this.script.setActions(actuallySelectedOptions);
            this.script.getActions().forEach(action -> Log.d(this, action.toString()));
            this.configureOptionTextView(this.script.getActions(), this.findViewById(R.id.script_actions));
            this.setScriptResizeLayoutVisibility();
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

    private void populateList(ArrayList<ScriptAction> actuallySelectedOptions, int size) {
        for (int i = 0; i < size; i++) actuallySelectedOptions.add(null);
    }

    private void setScriptResizeLayoutVisibility() {
        ConstraintLayout scriptResizeLayout = this.findViewById(R.id.script_resize_quality_layout);
        this.actionCompress = (ActionCompress) this.checkIfActionIsPresent(ActionCompress.class);
        if (this.actionCompress != null) {
            scriptResizeLayout.setVisibility(View.VISIBLE);
        } else {
            scriptResizeLayout.setVisibility(View.GONE);
        }
    }

    private ScriptAction checkIfActionIsPresent(Class<?> action) {
        return this.script.getActions().stream().filter(scriptAction -> scriptAction.getClass() == action).findFirst().orElse(null);
    }

    @Override
    public void switchHandler(View view, int position) {
        throw new UnsupportedOperationException();
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
