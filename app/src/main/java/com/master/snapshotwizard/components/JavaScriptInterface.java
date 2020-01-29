package com.master.snapshotwizard.components;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.master.snapshotwizard.utils.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

class JavaScriptInterface {
    private Context ctx;
    public static ArrayList<Element> selectedElements = new ArrayList<>();

    JavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    public void showHTML(String html) {
        Log.d(this, "showHTML: "+ html);
        new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
    }

    @JavascriptInterface
    public boolean processSelectedElement(String selectedElement){
        Log.d(this, "processSelectedElement: " + selectedElement);
        Document document = Jsoup.parse(selectedElement);
        Element element = document.body();
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

    private Element checkIfElementListContains(Element element) {
        for(Element e : selectedElements){
            if(e.html().equals(element.html())) return e;
        }
        return null;
    }
}