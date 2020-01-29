package com.master.snapshotwizard.components;

import android.webkit.JavascriptInterface;

import com.master.snapshotwizard.utils.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaScriptInterface {
    public static ArrayList<ElementWrapper> selectedElements = new ArrayList<>();
    private String[] validHTMLTags = {"p", "img"};

    @JavascriptInterface
    public boolean processSelectedElement(String selectedElement){
        Log.d(this, "processSelectedElement: " + selectedElement);
        ElementWrapper elementWrapper = new ElementWrapper(Jsoup.parse(selectedElement));
        if(!checkIfElementIsValidTag(elementWrapper)) return false;
        /* It contains the elems, return false, and remove border */
        ElementWrapper duplicateElement = checkIfElementListContains(elementWrapper);
        if (duplicateElement != null) {
            selectedElements.remove(duplicateElement);
            return false;
        }
        selectedElements.add(elementWrapper);
        if(elementWrapper.getElement().text().isEmpty()){
            /* Text */
        } else {
            /* Non-text */
        }
        return true;
    }

    private boolean checkIfElementIsValidTag(ElementWrapper elementWrapper) {
        String tag = elementWrapper.getNormalName();
        return Arrays.asList(validHTMLTags).contains(tag);
    }

    private ElementWrapper checkIfElementListContains(ElementWrapper elementWrapper) {
        for(ElementWrapper eW : selectedElements){
            if(eW.compareTo(elementWrapper) == 0)  return eW;
        }
        return null;
    }
}