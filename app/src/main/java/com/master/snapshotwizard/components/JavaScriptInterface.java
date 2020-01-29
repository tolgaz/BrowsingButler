package com.master.snapshotwizard.components;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.master.snapshotwizard.utils.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaScriptInterface {
    public static ArrayList<Element> selectedElements = new ArrayList<>();
    private String[] validHTMLTags = {"p", "img"};

    @JavascriptInterface
    public boolean processSelectedElement(String selectedElement){
        Log.d(this, "processSelectedElement: " + selectedElement);
        Document document = Jsoup.parse(selectedElement);
        Element element = document.body().child(0);
        if(!checkIfElementIsValidTag(element)) return false;
        /* It contains the elems, return false, and remove border */
        Element duplicateElement = checkIfElementListContains(element);
        if (duplicateElement != null) {
            selectedElements.remove(duplicateElement);
            return false;
        }
        selectedElements.add(element);
        if(document.text().isEmpty()){
            /* Text */
        } else {
            /* Non-text */
        }
        return true;
    }

    private boolean checkIfElementIsValidTag(Element element) {
        String tag = element.normalName();
        return Arrays.asList(validHTMLTags).contains(tag);
    }

    private Element checkIfElementListContains(Element element) {
        for(Element e : selectedElements){
            if(e.outerHtml().equals(element.outerHtml()))  return e;
        }
        return null;
    }
}