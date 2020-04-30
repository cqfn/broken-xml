package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public final class Element {

    private final long start;
    private final long end;
    private final List<Attribute> attributes;
    private final List<Element> children;
    private final List<Comment> comments;
    private final XmlHeadElement[] xmlHead;
    private final String name;
    private final Text text;

    public Element(final String name, final Text text, final long start, final long end) {
        this.name = name;
        this.text = text;
        this.start = start;
        this.end = end;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.xmlHead = new XmlHeadElement[]{ null };
    }

    void addChild(Element child) {
        this.children.add(child);
    }

    void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    void addCommnet(Comment comment) {
        this.comments.add(comment);
    }

    void addXmlHead(XmlHeadElement xmlHead) {
        this.xmlHead[0] = xmlHead;
    }

    boolean hasXmlHead() {
        return this.xmlHead[0] != null;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public XmlHeadElement getXmlHead() {
        return xmlHead[0];
    }
}
