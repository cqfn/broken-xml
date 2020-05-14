package com.guseyn.broken_xml;

import java.util.List;
import java.util.stream.Collectors;

public final class HeadElement {
    private final int start;
    private final int end;
    private final List<Attribute> attributes;

    HeadElement(
        final int start,
        final int end,
        final List<Attribute> attributes) {
        this.start = start;
        this.end = end;
        this.attributes = attributes;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public List<Attribute> attributes() {
        return attributes;
    }

    String json() {
        return String.format(
            "{ \"attributes:\": [ %s ], \"start\": %d, \"end\": %d }",
            this.attributes.stream().map(Attribute::json).collect(Collectors.joining(", ")),
            this.start,
            this.end
        );
    }
}
