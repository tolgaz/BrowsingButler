package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionAllText extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_text_title);
    private static int ID = 6;

    public SelectionAllText() {
        super(title, null, ID);
    }

    @Override
    public void execute() {
        super.execute();
    }
}
