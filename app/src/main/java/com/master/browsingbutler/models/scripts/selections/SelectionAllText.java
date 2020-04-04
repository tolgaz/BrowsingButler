package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;

public class SelectionAllText extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_text_title);
    private static int ID = 5;
    private static String MimeType = "text";
    // Is this god enought for text html types?
    private static String HTMLType = "p";


    public SelectionAllText() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(ElementWrapper elementWrapper) {
        return super.getSelection(elementWrapper, MimeType, HTMLType);
    }
}
