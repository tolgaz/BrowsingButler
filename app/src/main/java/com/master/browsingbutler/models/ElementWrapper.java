package com.master.browsingbutler.models;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

public class ElementWrapper implements Comparable {

    private Element element;
    private File file;
    private boolean chosen;
    private Boolean satisfiesSelection = null;
    private String text = null;

    public ElementWrapper(Document document) {
        this.element = document.body().child(0);
        this.chosen = false;
    }

    public String getNormalName() {
        return this.element.normalName();
    }

    public Element getElement() {
        return this.element;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ElementWrapper) {
            ElementWrapper elementWrapper = (ElementWrapper) o;
            if (this.element.outerHtml().equals(elementWrapper.getElement().outerHtml())) return 0;
        }
        return -1;
    }

    public boolean compareTo(Element toCompare) {
        return this.element.outerHtml().equals(toCompare.outerHtml());
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public boolean getChosen() {
        return this.chosen;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public Boolean getSatisfiesSelection() {
        return this.satisfiesSelection;
    }

    public void setSatisfiesSelection(Boolean satisfiesSelection) {
        this.satisfiesSelection = satisfiesSelection;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNotText() {
        return this.text == null || this.text.isEmpty();
    }

    public boolean isText() {
        return this.text != null && !this.text.isEmpty();
    }
}


