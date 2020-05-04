package com.xml.fixer;

import java.lang.reflect.Field;

public class ImprovedParsedXML {

    private final String xml;

    public ImprovedParsedXML(final String xml) {
        this.xml = xml;
    }

    public XmlDocument value() throws NoSuchFieldException, IllegalAccessException {

        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        final char[] chars = (char[]) field.get(xml);
        final int len = chars.length;
        assert len == xml.length();

        final XmlDocument doc = new XmlDocument(0, len);

        for (int i = 0; i < len; i++) {
            final char cur = chars[i];

            if (cur == '<' && i < len - 1 && chars[i + 1] == '/') {
                continue;
            }

            if (cur == '/' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '<' && i < len - 1 && chars[i + 1] == '?') {
                continue;
            }

            if (cur == '?' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '<' && i < len - 1 && chars[i + 1] == '!') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '?') {
                continue;
            }

            if (cur == '?' && i < len - 1 && chars[i + 1] == '>') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '-') {
                continue;
            }

            if (cur == '-' && i < len - 1 && chars[i + 1] == '>') {
                continue;
            }

            if (cur == '-' && i > 2 && chars[i - 1] == '-' && chars[i - 2] == '!' && chars[i - 3] == '<') {
                continue;
            }

            if (cur == '-' && i < len - 3 && chars[i + 1] == '-' && chars[i + 2] == '-' && chars[i + 3] == '>') {
                continue;
            }

            if (cur == '<') {
                continue;
            }

            if (cur == '>') {
                continue;
            }
        }
        return doc;
    }
}
