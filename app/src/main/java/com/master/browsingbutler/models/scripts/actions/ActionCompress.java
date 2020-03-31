package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

public class ActionCompress extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_compress_title);
    private static String description = App.getResourses().getString(R.string.script_action_compress_desc);

    public ActionCompress() {
        super(title, description);
    }

    @Override
    public void execute() {
        Log.d(this, "ActionCompress EXECUTING! ");

    }
}
