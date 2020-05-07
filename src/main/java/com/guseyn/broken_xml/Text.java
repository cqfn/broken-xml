package com.guseyn.broken_xml;

public final class Text {
    private final int start;
    private final int end;
    private final String value;

    Text(final String text, final int start, final int end) {
        this.start = start;
        this.end = end;
        this.value = text;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public String value() {
        return this.value;
    }
}
