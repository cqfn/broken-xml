package com.xml.fixer;

import java.util.ArrayList;
import java.util.List;

public class XmlHeadElement {
    private long start;
    private long end;
    private List<Attribute> attributes;

    public XmlHeadElement() {
        this.attributes = new ArrayList<>();
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    void setStart(long start) {
        this.start = start;
    }

    void setEnd(long end) {
        this.end = end;
    }

    void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
