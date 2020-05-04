package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public final class Element {

    private long start;
    private long end;
    private final List<Attribute> attributes;
    private final List<Element> children;
    private String name;
    private Text text;

    public Element() {
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
        return this.start;
    }

    public long getEnd() {
        return this.end;
    }

    public String getName() {
        return this.name;
    }

    void setStart(long start) {
        this.start = start;
    }

    void setEnd(long end) {
        this.end = end;
    }

    void setName(String name) {
        this.name = name;
    }

    void setText(Text text) {
        this.text = text;
    }
}
