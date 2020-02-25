package com.master.snapshotwizard.models;

public class ListItem {
    private String title;
    private String description;

    public ListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
