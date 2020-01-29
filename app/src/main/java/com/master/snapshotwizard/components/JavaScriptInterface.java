package com.master.snapshotwizard.components;

import android.webkit.JavascriptInterface;

import com.master.snapshotwizard.activities.WebpageRetrieverActivity;
import com.master.snapshotwizard.utils.Log;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaScriptInterface {
    public static ArrayList<ElementWrapper> selectedElements = new ArrayList<>();
    private String[] validHTMLTags = {"p", "img"};
    WebpageRetrieverActivity webpageRetrieverActivity;

    public JavaScriptInterface(WebpageRetrieverActivity webpageRetrieverActivity){
        this.webpageRetrieverActivity = webpageRetrieverActivity;
    }

    @JavascriptInterface
    public boolean processSelectedElement(String selectedElement){
        Log.d(this, "processSelectedElement: " + selectedElement);
        ElementWrapper elementWrapper = new ElementWrapper(Jsoup.parse(selectedElement));
        if(!checkIfElementIsValidTag(elementWrapper)) return false;
        /* It contains the elems, return false, and remove border */
        ElementWrapper duplicateElement = checkIfElementListContains(elementWrapper);
        if (duplicateElement != null) {
            selectedElements.remove(duplicateElement);
            if(selectedElements.isEmpty()){
                webpageRetrieverActivity.makeMagicWandButtonInvisible();
            }
            return false;
        }
        /* Shoiw magic wand if more htan 1 element is selected */
        selectedElements.add(elementWrapper);
        webpageRetrieverActivity.makeMagicWandButtonVisible();
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