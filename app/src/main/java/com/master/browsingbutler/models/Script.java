package com.master.browsingbutler.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Script implements Serializable {
    private static int scriptCounter = 0;

    public enum Type {
        PREMADE, CUSTOM
    }

    private String title;
    private String description;
    private boolean isPremade;
    private int ID;

    public Script(String title, String description, boolean isPremade) {
        this.title = title;
        this.description = description;
        this.isPremade = isPremade;
        this.ID = scriptCounter++;
    }

    public Script(String title, String description) {
        this.title = title;
        this.description = description;
        this.isPremade = false;
        this.ID = scriptCounter++;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPremade() {
        return this.isPremade;
    }

    public int getID() {
        return this.ID;
    }

    @NonNull
    @Override
    public String toString() {
        return "SCRIPT. Title: " + this.title + ", description: " + this.description;
    }
}
