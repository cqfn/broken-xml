package com.xml.fixer;

public final class Comment {

    private final int start;
    private final int end;
    private final String text;

    public Comment(final int start, final int end, final String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getText() {
        return text;
    }
}
