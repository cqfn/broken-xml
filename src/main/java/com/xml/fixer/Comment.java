package com.xml.fixer;

public final class Comment {

    private final long start;
    private final long end;
    private final String text;

    public Comment(final long start, final long end, final String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getText() {
        return text;
    }
}
