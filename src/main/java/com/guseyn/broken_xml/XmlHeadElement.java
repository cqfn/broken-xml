package com.guseyn.broken_xml;

import java.util.List;

public final class XmlHeadElement {
    private final long start;
    private final long end;
    private final List<Attribute> attributes;

    public XmlHeadElement(
        final long start,
        final long end,
        final List<Attribute> attributes) {
        this.start = start;
        this.end = end;
        this.attributes = attributes;
    }

    public long getStart() {
        return this.start;
    }

    public long getEnd() {
        return this.end;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
