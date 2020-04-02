package com.master.browsingbutler.models.scripts.selections;

import androidx.annotation.NonNull;

import com.master.browsingbutler.models.scripts.Executable;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptOption;
import com.master.browsingbutler.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class ScriptSelection implements ScriptOption, Executable {

    private static List<ScriptSelection> scriptSelections;
    private String title;
    private String description;
    private int ID;

    public ScriptSelection(String title, int ID) {
        this.title = title;
        this.description = null;
        this.ID = ID;
    }

    public ScriptSelection(String title, String description, int ID) {
        this(title, ID);
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

    public static void initScriptSelections() {
        ArrayList<ScriptSelection> selections = new ArrayList<>();
        selections.add(new SelectionAllElements());
        selections.add(new SelectionFirstElement());
        selections.add(new SelectionLastElement());
        selections.add(new SelectionFirstXElements());
        selections.add(new SelectionLastXElements());
        selections.add(new SelectionAllPictures());
        selections.add(new SelectionAllText());
        ScriptSelection.setScriptSelections(selections);
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

