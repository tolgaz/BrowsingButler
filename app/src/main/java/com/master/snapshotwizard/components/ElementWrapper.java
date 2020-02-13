package com.master.snapshotwizard.components;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ElementWrapper implements Comparable{

    private Element element;
    private Document document;
    private Element mediaElement;

    public ElementWrapper(Document document){
        this.document = document;
        this.element = document.body().child(0);
    }

    public String getNormalName(){
        return element.normalName();
    }

    public String getMediaElementNormalName(){
        return mediaElement.normalName();
    }


    public Element getElement() {
        return element;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof ElementWrapper){
            ElementWrapper elementWrapper = (ElementWrapper) o;
            if(this.element.outerHtml().equals(elementWrapper.getElement().outerHtml())) return 0;
        }
        return -1;
    }

    public Element getMediaElement() {
        return mediaElement;
    }

    public void setMediaElement(Element mediaElement) {
        this.mediaElement = mediaElement;
    }
}

