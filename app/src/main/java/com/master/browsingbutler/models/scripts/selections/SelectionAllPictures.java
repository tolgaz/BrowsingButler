package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.scripts.Script;

public class SelectionAllPictures extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_pictures_title);
    private static int ID = 5;

    public SelectionAllPictures() {
        super(title, null, ID);
    }

    public int[] getSelection() {
        return new int[]{0};
    }

    @Override
    public void execute(Script script) {
        super.execute(script);
    }
}
