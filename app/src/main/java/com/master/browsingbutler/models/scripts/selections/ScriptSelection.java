package com.master.browsingbutler.models.scripts.selections;

import androidx.annotation.NonNull;

import com.master.browsingbutler.models.scripts.Executable;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptOption;
import com.master.browsingbutler.utils.Log;

import java.util.List;

public class ScriptSelection implements ScriptOption, Executable {
    private static int selectionCounter = 0;
    private int ID;
    private static List<ScriptSelection> scriptSelections;
    private String title;
    private String description;

    public ScriptSelection(String title) {
        this.title = title;
        this.description = null;
        this.ID = selectionCounter++;
    }

    public ScriptSelection(String title, String description) {
        this(title);
        this.description = description;
    }

    public static List<ScriptSelection> getScriptSelections() {
        return scriptSelections;
    }

    public static void setScriptSelections(List<ScriptSelection> scriptSelections) {
        ScriptSelection.scriptSelections = scriptSelections;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Script.Option getOptionType() {
        return Script.Option.SELECTION;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + this.title + "\n Desc: " + this.description;
    }

    @Override
    public void execute() {
        Log.d(this, "Executing script! " + this.toString());
        throw new UnsupportedOperationException();
    }
}

