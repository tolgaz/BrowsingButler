package com.master.browsingbutler.components;

import android.webkit.JavascriptInterface;

import com.master.browsingbutler.activities.WebpageRetrieverActivity;
import com.master.browsingbutler.models.ElementWrapper;
import com.master.browsingbutler.utils.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaScriptInterface {
    private static List<ElementWrapper> selectedElements = generateTestData();
    private String[] validHTMLTags = {"img", "video"};
    WebpageRetrieverActivity webpageRetrieverActivity;

    public JavaScriptInterface(WebpageRetrieverActivity webpageRetrieverActivity) {
        this.webpageRetrieverActivity = webpageRetrieverActivity;
    }

    @JavascriptInterface
    public boolean processSelectedElement(String selectedElement) {
        Log.d(this, "processSelectedElement: " + selectedElement);
        ElementWrapper elementWrapper = new ElementWrapper(Jsoup.parse(selectedElement));
        if (!this.checkIfElementIsValidTag(elementWrapper)) return false;
        /* It contains the elems, return false, and remove border */
        ElementWrapper duplicateElement = this.checkIfElementListContains(elementWrapper);
        if (duplicateElement != null) {
            selectedElements.remove(duplicateElement);
            if (selectedElements.isEmpty()) {
                this.webpageRetrieverActivity.makeMagicWandButtonInvisible();
            }
            return false;
        }
        /* Shoiw magic wand if more htan 1 element is selected */
        selectedElements.add(elementWrapper);
        this.webpageRetrieverActivity.makeMagicWandButtonVisible();
        if (elementWrapper.getElement().text().isEmpty()) {
            /* Text */
        } else {
            /* Non-text */
        }
        return true;
    }

    private boolean checkIfElementIsValidTag(ElementWrapper elementWrapper) {
        /* Might be a div with a <img> inside. How do we retrieve? */
        /* Currently grab element and find the img tag inside */
        Elements elements = elementWrapper.getElement().getAllElements();
        Element element = null;
        for (Element tmpElement : elements) {
            element = tmpElement;
            if (Arrays.asList(this.validHTMLTags).contains(element.normalName())) break;
            element = null;
        }
        if (element != null) {
            elementWrapper.setMediaElement(element);
            return true;
        }
        return false;
    }

    private ElementWrapper checkIfElementListContains(ElementWrapper elementWrapper) {
        for (ElementWrapper eW : selectedElements) {
            if (eW.compareTo(elementWrapper) == 0) return eW;
        }
        return null;
    }

    public static List<ElementWrapper> getSelectedElements() {
        return selectedElements;
    }

    private static List<ElementWrapper> generateTestData() {
        return Stream.of(
                new ElementWrapper(Jsoup.parse("<video id=\"video-G2UMu6l\" class=\"Video-element\" poster=\"https://i.imgur.com/G2UMu6l_d.jpg?maxwidth=640&amp;shape=thumb&amp;fidelity=medium\" src=\"https://i.imgur.com/G2UMu6l.mp4\" type=\"video/mp4\" loop=\"\" playsinline=\"\" x-webkit-airplay=\"deny\" style=\"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\"></video>")),
                new ElementWrapper(Jsoup.parse("<img class=\"Image\" src=\"https://i.imgur.com/spu7fxm_d.jpg?maxwidth=640&amp;shape=thumb&amp;fidelity=medium\" style=\"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\">")),
                new ElementWrapper(Jsoup.parse("<img class=\"Image\" src=\"https://i.imgur.com/MHhF1n9_d.jpg?maxwidth=640&amp;shape=thumb&amp;fidelity=medium\" style=\"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\">")),
                new ElementWrapper(Jsoup.parse("<img class=\"Image\" src=\"https://i.imgur.com/LmxlQup_d.jpg?maxwidth=640&amp;shape=thumb&amp;fidelity=medium\" style=\"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\">")),
                new ElementWrapper(Jsoup.parse("<video id=\"video-DcJglzN\" class=\"Video-element\" poster=\"https://i.imgur.com/DcJglzN_d.jpg?maxwidth=640&amp;shape=thumb&amp;fidelity=medium\" src=\"https://i.imgur.com/DcJglzN.mp4\" type=\"video/mp4\" loop=\"\" playsinline=\"\" x-webkit-airplay=\"deny\" style=\"border-color: red; border-style: solid; border-width: 3px; box-sizing: border-box;\"></video>"))
        ).collect(Collectors.toList());
    }

}