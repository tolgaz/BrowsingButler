package com.master.browsingbutler.components;

import com.master.browsingbutler.App;
import com.master.browsingbutler.R;
import com.master.browsingbutler.models.scripts.Script;
import com.master.browsingbutler.models.scripts.actions.ActionCompress;
import com.master.browsingbutler.models.scripts.actions.ActionDownload;
import com.master.browsingbutler.models.scripts.actions.ScriptAction;
import com.master.browsingbutler.models.scripts.selections.ScriptSelection;
import com.master.browsingbutler.models.scripts.selections.SelectionAllElements;

import java.util.ArrayList;
import java.util.List;

public class Scripts {

    private static List<Script> allScripts = new ArrayList<>();

    public static void initScripts() {
        List<Script> allScripts = new ArrayList<>();

        allScripts.add(getCompressTo50());
        allScripts.add(getDownloadAndSendScript());
        allScripts.add(getSaveAndCompressScript());
        setAllScripts(allScripts);
    }

    private static Script getCompressTo50() {
        /* Compress all elements to 50% */
        return new Script(App.getResourses().getString(R.string.compresTo50_title), App.getResourses().getString(R.string.compresTo50_desc), true);
    }

    private static Script getDownloadAndSendScript() {
        /* Download and send elements */
        return new Script(App.getResourses().getString(R.string.downloadAndSend_title), App.getResourses().getString(R.string.downloadAndSend_desc), true);
    }

    private static Script getSaveAndCompressScript() {
        List<ScriptAction> saveAndCompressActions = new ArrayList<>();
        List<ScriptSelection> saveAndCompressSelections = new ArrayList<>();

        saveAndCompressActions.add(new ActionDownload());
        saveAndCompressActions.add(new ActionCompress());

        saveAndCompressSelections.add(new SelectionAllElements());

        return new Script("Save and compress all elements", "title hoe", true, saveAndCompressActions, saveAndCompressSelections);
    }

    public static List<Script> getAllScripts() {
        return allScripts;
    }

    public static void setAllScripts(List<Script> allScripts) {
        Scripts.allScripts = allScripts;
    }

    public static void addScript(Script script) {
        allScripts.add(script);
    }

    public static void deleteScript(Script script) {
        /* Delete from list and update indices */
        int IDOfScriptToDelete = script.getID();
        List<Script> scripts = Scripts.getAllScripts();

        if (IDOfScriptToDelete != scripts.size() - 1) {
            for (int i = IDOfScriptToDelete + 1; i < scripts.size(); i++) {
                /* for each we want to decrement counter */
                Script currScript = scripts.get(i);
                int oldID = currScript.getID();
                currScript.setID(oldID - 1);
            }
        }
        scripts.remove(IDOfScriptToDelete);
        assert Scripts.getAllScripts().size() == Scripts.getAllScripts().get(Scripts.getAllScripts().size() - 1).getID();
    }
}
