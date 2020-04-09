package com.master.browsingbutler.models.scripts.selections;

import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.interfaces.ScriptOption;
import com.master.browsingbutler.models.scripts.interfaces.Selectable;
import com.master.browsingbutler.utils.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptSelection implements ScriptOption, Selectable {

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
    public ScriptOption clone(ScriptOption scriptOption) {
        return null;
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
        selections.add(new SelectionAllPictures());
        selections.add(new SelectionAllVideos());
        selections.add(new SelectionAllText());
        ScriptSelection.setScriptSelections(selections);
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + this.title + "\nDesc: " + this.description;
    }

    @Override
    public boolean getSelection(int index, int size) {
        Log.d(this, "Getting selection! " + this.toString());
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getSelection(ElementWrapper elementWrapper) {
        Log.d(this, "Getting boolean-selection! " + this.toString());
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getType() {
        throw new UnsupportedOperationException();
    }

    protected boolean getSelection(ElementWrapper elementWrapper, String mimeType, String htmlType) {
        File file = elementWrapper.getFile();
        if (file != null) {
            String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath()));
            return type != null && type.contains(mimeType);
        } else {
            return elementWrapper.getNormalName().equals(htmlType);
        }
    }
}

