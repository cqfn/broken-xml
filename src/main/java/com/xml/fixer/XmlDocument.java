package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public class XmlDocument {
    private int start;
    private int end;
    private XmlHeadElement xmlHead;
    private final List<Comment> comments;
    private final List<Element> roots;

    public XmlDocument(final int start, final int end) {
        this.start = start;
        this.end = end;
        this.xmlHead = null;
        this.comments = new ArrayList<>();
        this.roots = new ArrayList<>();
    }


    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }


    public XmlHeadElement getHead() {
        return this.xmlHead;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Element> getRoots() {
        return this.roots;
    }

    public boolean hasHead() {
        return this.xmlHead != null;
    }

    void setHead(XmlHeadElement head) {
        this.xmlHead = head;
    }

    void addComment(Comment comment) {
        this.comments.add(comment);
    }

    void addRoot(Element element) {
        this.roots.add(element);
    }
}
