package com.master.browsingbutler.models.scripts.actions;

import androidx.annotation.NonNull;

import com.master.browsingbutler.models.scripts.Executable;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptOption;

import java.util.ArrayList;
import java.util.List;

public class ScriptAction implements ScriptOption, Executable {

    private static List<ScriptAction> scriptActions;
    private String title;
    private String description;
    private int ID;

    /* compression settings */
    private int quality;
    private int width;
    private int height;

    public ScriptAction(String title, String description, int ID) {
        this.title = title;
        this.description = description;
        this.ID = ID;
    }

    public ScriptAction(String title, String description, int ID, int quality, int width, int height) {
        this(title, description, ID);
        this.quality = quality;
        this.width = width;
        this.height = height;
    }

    public static List<ScriptAction> getScriptActions() {
        return scriptActions;
    }

    private static void setScriptActions(List<ScriptAction> scriptActions) {
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

    @Override
    public int getID() {
        return this.ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Script.Option getOptionType() {
        return Script.Option.ACTION;
    }

    public static void initScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();
        actions.add(new ActionDownload());
        actions.add(new ActionCompress());
        actions.add(new ActionFileCreator());
        actions.add(new ActionShare());
        ScriptAction.setScriptActions(actions);
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

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
