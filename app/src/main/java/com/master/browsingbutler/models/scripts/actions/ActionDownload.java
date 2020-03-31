package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class ActionDownload extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_download_title);
    private static String description = App.getResourses().getString(R.string.script_action_download_desc);

    public ActionDownload() {
        super(title, description);
    }
}
