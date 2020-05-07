package com.guseyn.broken_xml;

public final class Comment {

    private final int start;
    private final int end;
    private final String text;

    Comment(final String text, final int start, final int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public String text() {
        return this.text;
    }
}
