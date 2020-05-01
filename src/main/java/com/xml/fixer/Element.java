package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public final class Element {

    private long start;
    private long end;
    private final List<Attribute> attributes;
    private final List<Element> children;
    private final String name;
    private final Text text;

    public Element(final String name, final Text text) {
        this.name = name;
        this.text = text;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    void addChild(Element child) {
        this.children.add(child);
    }

    void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    void setStart(long start) {
        this.start = start;
    }

    void setEnd(long end) {
        this.end = end;
    }
}
