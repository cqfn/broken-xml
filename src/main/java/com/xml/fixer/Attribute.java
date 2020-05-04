package com.xml.fixer;

public class Attribute {

    private final String name;
    private final String value;
    private final int nameStart;
    private final int nameEnd;
    private final int valueStart;
    private final int valueEnd;

    public Attribute(
        final String name,
        final String value,
        final int nameStart,
        final int nameEnd,
        final int valueStart,
        final int valueEnd
    ) {
        this.name = name;
        this.value = value;
        this.nameStart = nameStart;
        this.nameEnd = nameEnd;
        this.valueStart = valueStart;
        this.valueEnd = valueEnd;
    }

    public int getNameStart() {
        return nameStart;
    }

    public int getNameEnd() {
        return nameEnd;
    }

    public int getValueStart() {
        return valueStart;
    }

    public int getValueEnd() {
        return valueEnd;
    }
}
