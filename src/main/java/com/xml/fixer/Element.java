package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public final class Element {
    private final List<Attribute> attributes;
    private final List<Element> children;
    //private final Text text;
    private final int start;
    private int end;
    private final String name;

    public Element(final String name, final int start) {
        this.start = start;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.end = 0;
        this.name = name;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String getName() {
        return this.name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Element> getChildren() {
        return children;
    }

    void correctEnd(final int end) {
        this.end = end;
    }
}
