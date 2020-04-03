package com.master.browsingbutler.models.scripts.actions;

import androidx.annotation.NonNull;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.CompressResizeActivity;
import com.master.browsingbutler.models.scripts.ScriptOption;
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
        CompressResizeActivity.applyCompressResize(this.getQuality(), String.valueOf(this.getWidth()), String.valueOf(this.getHeight()), true);
    }

    public static int getStaticID() {
        return ID;
    }

    @Override
    public ScriptOption clone(ScriptOption scriptOption) {
        ActionCompress castedOption = (ActionCompress) scriptOption;
        return new ActionCompress(castedOption.getQuality(), castedOption.getWidth(), castedOption.getHeight());
    }

    @NonNull
    @Override
    public String toString() {
        return "ActionCompress. Width: " + this.getWidth() + ", height: " + this.getHeight() + ", quality: " + this.getQuality() + ". \n" + super.toString();
    }
}
