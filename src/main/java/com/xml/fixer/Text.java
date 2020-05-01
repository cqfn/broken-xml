package com.xml.fixer;

public class Text {
    private final long start;
    private final long end;
    private final String value;

    public Text(final long start, final long end, final String text) {
        this.start = start;
        this.end = end;
        this.value = text;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getValue() {
        return value;
    }
}
