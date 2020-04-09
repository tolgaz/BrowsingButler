package com.master.browsingbutler.components;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.actions.ActionCompress;
import com.master.browsingbutler.models.scripts.actions.ActionDownload;
import com.master.browsingbutler.models.scripts.actions.ScriptAction;
import com.master.browsingbutler.models.scripts.interfaces.ScriptOption;
import com.master.browsingbutler.models.scripts.selections.ScriptSelection;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiSpinner<T> extends AppCompatSpinner implements OnMultiChoiceClickListener, OnCancelListener {

    private List<T> items;
    private boolean[] selected;
    private MultiSpinnerListener listener;
    private Script.Option optionType;
    private String title;
    private int checkedCounter;
    private int[] order;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked && this.optionType == Script.Option.ACTION && which == ActionCompress.getStaticID()) {
            /* if compression is slsected check download aswell */
            ((AlertDialog) dialog).getListView().setItemChecked(ActionDownload.getStaticID(), true);
            this.selected[ActionDownload.getStaticID()] = true;
            this.order[ActionDownload.getStaticID()] = this.checkedCounter++;
        }
        if (isChecked) {
            this.order[which] = this.checkedCounter;
        } else {
            int orderValue = this.order[which];
            for (int i = 0; i < this.order.length; i++) {
                if (this.order[i] > orderValue) this.order[i] -= 1;
            }
            this.order[which] = -1;
        }
        this.checkedCounter = Math.max(isChecked ? this.checkedCounter + 1 : this.checkedCounter - 1, 0);
        this.selected[which] = isChecked;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.listener.onItemsSelected(this.selected, this.optionType, this.order);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMultiChoiceItems(this.getTitleAndDescriptionFromScriptActions(), this.selected, this);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.cancel());
        builder.setTitle(this.title);
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    private CharSequence[] getTitleAndDescriptionFromScriptActions() {
        CharSequence[] cs = new CharSequence[this.items.size()];
        AtomicInteger index = new AtomicInteger();
        this.items.forEach(scriptElement -> {
            if (this.optionType == Script.Option.ACTION) {
                ScriptAction action = (ScriptAction) scriptElement;
                cs[index.getAndIncrement()] =
                        Html.fromHtml(action.getTitle() + "<br><small><small>" + action.getDescription() + "</small></small>", 0);
            } else {
                ScriptSelection selection = (ScriptSelection) scriptElement;
                cs[index.getAndIncrement()] = selection.getTitle();
            }
        });

        return cs;
    }

    public void setItems(List<T> items, List<T> scriptElement, Script.Option optionType, String title, MultiSpinnerListener listener) {
        this.items = items;
        this.optionType = optionType;
        this.title = title;
        this.listener = listener;
        this.checkedCounter = scriptElement.size();
        this.order = new int[items.size()];

        // can this be done better??
        this.selected = new boolean[items.size()];
        for (int i = 0; i < items.size(); i++) {
            boolean match = false;
            ScriptOption currScriptOption = (ScriptOption) items.get(i);
            int j;
            for (j = 0; j < scriptElement.size(); j++) {
                if (currScriptOption.getID() == ((ScriptOption) scriptElement.get(j)).getID()) {
                    match = true;
                    break;
                }
            }
            this.selected[i] = match;
            this.order[i] = match ? j : -1;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item);
        this.setAdapter(adapter);
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected, Script.Option optionType, int[] order);
    }
}