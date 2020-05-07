package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public final class XmlDocument {
    private final int start;
    private final int end;
    private final List<XmlHeadElement> xmlHeads;
    private final List<Comment> comments;
    private final List<Element> roots;

    public XmlDocument(final int start, final int end) {
        this.start = start;
        this.end = end;
        this.xmlHeads = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.roots = new ArrayList<>();
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public List<XmlHeadElement> getHeads() {
        return this.xmlHeads;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Element> getRoots() {
        return this.roots;
    }
}
