package com.master.browsingbutler.models.scripts.selections;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.ElementWrapper;

import static com.master.browsingbutler.models.scripts.interfaces.Selectable.Type.FILETYPES;

public class SelectionAllPictures extends ScriptSelection {

    private static String title = App.getResourses().getString(R.string.script_selection_all_pictures_title);
    private static int ID = 3;
    private static String MimeType = "image";
    //  HTML TAG for images
    private static String[] HTMLType = {"img"};
    private static Type TYPE = FILETYPES;

    SelectionAllPictures() {
        super(title, null, ID);
    }

    @Override
    public boolean getSelection(ElementWrapper elementWrapper) {
        return super.getSelection(elementWrapper, MimeType, HTMLType);
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}
