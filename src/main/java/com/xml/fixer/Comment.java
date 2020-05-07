package com.xml.fixer;

public final class Comment {

    private final int start;
    private final int end;
    private final String text;

    public Comment(final String text, final int start, final int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String getText() {
        return this.text;
    }
}
