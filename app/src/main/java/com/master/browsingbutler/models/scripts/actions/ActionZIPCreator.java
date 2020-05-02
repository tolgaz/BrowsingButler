package com.master.browsingbutler.models.scripts.actions;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.interfaces.ScriptOption;
import com.master.browsingbutler.utils.FileUtils;
import com.master.browsingbutler.utils.Log;

public class ActionZIPCreator extends ScriptAction {

    private static String title = App.getResourses().getString(R.string.script_action_file_creator_title);
    private static String description = App.getResourses().getString(R.string.script_action_file_creator_desc);
    private static int ID = 2;

    public ActionZIPCreator() {
        super(title, description, ID);
    }

    public static int getStaticID() {
        return ID;
    }

    @Override
    public ScriptOption clone(ScriptOption scriptOption) {
        return new ActionZIPCreator();
    }

    @Override
    public void execute(Script script) {
        Log.d(this, "ActionZIPCreator EXECUTING! ");
        script.setZIPFile(FileUtils.createZIPFromDownloadedFiles(true));
    }
}
