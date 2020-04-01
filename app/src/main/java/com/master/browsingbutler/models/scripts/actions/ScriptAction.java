package com.master.browsingbutler.models.scripts.actions;

import androidx.annotation.NonNull;

import com.master.browsingbutler.models.scripts.Executable;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptOption;

import java.util.List;

public class ScriptAction implements ScriptOption, Executable {
    private static int actionCounter = 0;
    private int ID;
    private static List<ScriptAction> scriptActions;
    private String title;
    private String description;

    public ScriptAction(String title, String description) {
        this.title = title;
        this.description = description;
        this.ID = actionCounter++;
    }

    public static List<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    public static void setScriptActions(List<ScriptAction> scriptActions) {
        ScriptAction.scriptActions = scriptActions;
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
        return Script.Option.ACTION;
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
        throw new UnsupportedOperationException();
    }
}
