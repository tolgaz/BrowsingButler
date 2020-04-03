package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.activities.SaveActivity;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.ScriptOption;
import com.master.browsingbutler.utils.Log;

public class ActionDownload extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_download_title);
    private static String description = App.getResourses().getString(R.string.script_action_download_desc);
    private static int ID = 0;

    public ActionDownload() {
        super(title, description, ID);
    }

    public static int getStaticID() {
        return ID;
    }

    @Override
    public ScriptOption clone(ScriptOption scriptOption) {
        return new ActionDownload();
    }

    @Override
    public void execute(Script script) {
        Log.d(this, "ACTIONDOWNLOAD EXECUTING! DOWNLOADING (all) ELEMENTS");
        SaveActivity.downloadAllElements(false);
    }
}
