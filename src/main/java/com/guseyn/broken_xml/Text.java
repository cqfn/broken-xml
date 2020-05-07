package com.guseyn.broken_xml;

public final class Text {
    private final int start;
    private final int end;
    private final String value;

    public Text(final String text, final int start, final int end) {
        this.start = start;
        this.end = end;
        this.value = text;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String getValue() {
        return this.value;
    }
}
