package com.master.browsingbutler.models;

public interface ScriptOption {
    Script.Option getOptionType();

    String getTitle();

    String getDescription();

    int getID();
}
