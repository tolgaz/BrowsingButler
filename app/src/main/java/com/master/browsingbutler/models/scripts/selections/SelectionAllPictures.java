package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;

public class SelectionAllPictures extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_pictures_title);

    public SelectionAllPictures() {
        super(title, null);
    }
}
