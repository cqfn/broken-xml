package com.xml.fixer;

public final class Text {
    private final int start;
    private final int end;
    private final String value;

    public Text(final int start, final int end, final String text) {
        this.start = start;
        this.end = end;
        this.value = text;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getValue() {
        return value;
    }
}
