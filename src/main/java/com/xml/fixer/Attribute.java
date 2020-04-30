package com.xml.fixer;

public class Attribute {

    private final String name;
    private final String value;
    private final long nameStart;
    private final long nameEnd;
    private final long valueStart;
    private final long valueEnd;

    public Attribute(
        final String name,
        final String value,
        final long nameStart,
        final long nameEnd,
        final long valueStart,
        final long valueEnd
    ) {
        this.name = name;
        this.value = value;
        this.nameStart = nameStart;
        this.nameEnd = nameEnd;
        this.valueStart = valueStart;
        this.valueEnd = valueEnd;
    }

    public long getNameStart() {
        return nameStart;
    }

    public long getNameEnd() {
        return nameEnd;
    }

    public long getValueStart() {
        return valueStart;
    }

    public long getValueEnd() {
        return valueEnd;
    }
}
