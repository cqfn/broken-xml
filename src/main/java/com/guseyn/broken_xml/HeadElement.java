package com.guseyn.broken_xml;

import java.util.List;

public final class HeadElement {
    private final long start;
    private final long end;
    private final List<Attribute> attributes;

    HeadElement(
        final long start,
        final long end,
        final List<Attribute> attributes) {
        this.start = start;
        this.end = end;
        this.attributes = attributes;
    }

    public long start() {
        return this.start;
    }

    public long end() {
        return this.end;
    }

    public List<Attribute> attributes() {
        return attributes;
    }
}
