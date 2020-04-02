package com.master.browsingbutler.models.scripts.actions;

import androidx.annotation.NonNull;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

public class ActionCompress extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_compress_title);
    private static String description = App.getResourses().getString(R.string.script_action_compress_desc);
    private static int ID = 1;

    ActionCompress() {
        super(title, description, ID);
    }

    public ActionCompress(int quality, int width, int height) {
        super(title, description, ID, quality, width, height);
    }

    @Override
    public void execute() {
        Log.d(this, "ActionCompress EXECUTING! ");
    }

    public static int getStaticID() {
        return ID;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActionCompress! Height: " + this.getHeight() + ", width: " + this.getWidth() + ", quality: " + this.getQuality() + ". " + super.toString();
    }
}
