package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.utils.Log;

public class ActionShare extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_share_title);
    private static String description = App.getResourses().getString(R.string.script_action_share_desc);
    private static int ID = 3;

    ActionShare() {
        super(title, description, ID);
    }

    public static int getStaticID() {
        return ID;
    }

    @Override
    public void execute() {
        Log.d(this, "ActionShare EXECUTING! ");

    }
}
