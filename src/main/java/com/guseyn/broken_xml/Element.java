package com.guseyn.broken_xml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Element {
    private final String name;
    private final int start;
    private final List<Attribute> attributes;
    private final List<Element> children;
    private final List<Text> texts;
    private int end;

    Element(final String name, final int start) {
        this.name = name;
        this.start = start;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.texts = new ArrayList<>();
        this.end = 0;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public String name() {
        return this.name;
    }

    public List<Attribute> attributes() {
        return this.attributes;
    }

    public List<Element> children() {
        return this.children;
    }

    public List<Text> texts() {
        return this.texts;
    }

    void correctEnd(final int end) {
        this.end = end;
    }

    String json() {
        return String.format(
            "{ \"name\": \"%s\", \"attributes:\": [ %s ], \"texts\": [ %s ], \"children\": [ %s ], \"start\": %d, \"end\": %d }",
            this.name,
            this.attributes.stream().map(Attribute::json).collect(Collectors.joining(", ")),
            this.texts.stream().map(Text::json).collect(Collectors.joining(", ")),
            this.children.stream().map(Element::json).collect(Collectors.joining(", ")),
            this.start,
            this.end
        );
    }
}
