package com.master.snapshotwizard.models;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

public class ElementWrapper implements Comparable {

    private Element element;
    private Document document;
    private Element mediaElement;
    private File file;
    private Boolean chosen;

    public ElementWrapper(Document document) {
        this.document = document;
        this.element = document.body().child(0);
        this.chosen = false;
    }

    public String getNormalName() {
        return this.element.normalName();
    }

    public String getMediaElementNormalName() {
        return this.mediaElement.normalName();
    }


    public Element getElement() {
        return this.element;
    }

    public Document getDocument() {
        return this.document;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ElementWrapper) {
            ElementWrapper elementWrapper = (ElementWrapper) o;
            if (this.element.outerHtml().equals(elementWrapper.getElement().outerHtml())) return 0;
        }
        return -1;
    }

    public Element getMediaElement() {
        return this.mediaElement;
    }

    public void setMediaElement(Element mediaElement) {
        this.mediaElement = mediaElement;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public Boolean getChosen() {
        return this.chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }
}

