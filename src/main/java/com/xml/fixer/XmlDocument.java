package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public class XmlDocument {
    private long start;
    private long end;
    private final XmlHeadElement[] xmlHead;
    private final List<Comment> comments;
    private final List<Element> elements;

    public XmlDocument(final long start, final long end) {
        this.start = start;
        this.end = end;
        this.xmlHead = new XmlHeadElement[]{ null };
        this.comments = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    public XmlHeadElement getHead() {
        return this.xmlHead[0];
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public boolean hasHead() {
        return this.xmlHead[0] != null;
    }

    void addHead(XmlHeadElement head) {
        this.xmlHead[0] = head;
    }

    void addComment(Comment comment) {
        this.comments.add(comment);
    }

    void addElement(Element element) {
        this.elements.add(element);
    }
}
