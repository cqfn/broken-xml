package com.xml.fixer;

public class ParsedXml {

    private final String xml;

    public ParsedXml(final String xml) {
        this.xml = xml;
    }

    public String value() {
        return xml;
    }
}
