package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

public class ActionFileCreator extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_file_creator_title);
    private static String description = App.getResourses().getString(R.string.script_action_file_creator_desc);

    public ActionFileCreator() {
        super(title, description);
    }

    @Override
    public void execute() {
        Log.d(this, "ActionFileCreator EXECUTING! ");

    }
}
