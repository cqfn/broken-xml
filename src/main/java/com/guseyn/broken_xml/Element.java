package com.guseyn.broken_xml;

import java.util.ArrayList;
import java.util.List;

public final class Element {
    private final List<Attribute> attributes;
    private final List<Element> children;
    private final List<Text> texts;
    private final int start;
    private int end;
    private final String name;

    public Element(final String name, final int start) {
        this.start = start;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.texts = new ArrayList<>();
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
        return this.attributes;
    }

    public List<Element> getChildren() {
        return this.children;
    }

    public List<Text> getTexts() {
        return this.texts;
    }

    void correctEnd(final int end) {
        this.end = end;
    }
}
