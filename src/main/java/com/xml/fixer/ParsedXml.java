package com.xml.fixer;

import java.lang.reflect.Field;
import java.util.Stack;

public final class ParsedXml {

    private final String xml;

    public ParsedXml(final String xml) {
        this.xml = xml;
    }

    public Element value() throws NoSuchFieldException, IllegalAccessException {

        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        final char[] chars = (char[]) field.get(xml);
        final int len = chars.length;
        assert len == xml.length();

        final Stack<Element> elements = new Stack<>();

        boolean xmlHeadElmStarted = false;
        boolean xmlHeadElmNameFinished = false;
        boolean newElmStarted = false;
        long newElmStart = 0;
        long newElmEnd = 0;
        boolean newAttrNameStarted = false;
        long newAttrNameStart = 0;
        long newAttrNameEnd = 0;
        boolean newAttrValueStarted = false;
        long newAttrValueStart = 0;
        long newAttrValueEnd = 0;
        boolean newCommentStarted = false;
        boolean newTextStarted = false;
        StringBuilder curElmName = null;
        StringBuilder curAttrName = null;
        StringBuilder curAttrValue = null;
        StringBuilder curText = null;
        StringBuilder curCommentText = null;
        final Element root = new Element(null, null, 0, len);
        Element inCurrentElm = root;
        elements.push(inCurrentElm);

        for (int i = 0; i < len; i++) {
            final char cur = chars[i];
            if (cur == '<') {
                newElmStarted = !newElmStarted;
                continue;
            }
            if (cur == '>' && xmlHeadElmStarted) {
                xmlHeadElmStarted = false;
            }
            if (cur == '?' && newElmStarted && !newAttrNameStarted && !newAttrValueStarted) {
                xmlHeadElmStarted = true;
                if (!root.hasXmlHead()) {
                    XmlHeadElement head = new XmlHeadElement();
                    head.setStart(i - 4);
                    root.addXmlHead(head);
                }
                continue;
            }
            if (isXMLChar(cur) &&
                xmlHeadElmStarted &&
                !newAttrNameStarted &&
                !newAttrValueStarted) {
                if (cur == 'x') {
                    xmlHeadElmNameFinished = false;
                    continue;
                }
                if (cur == 'l') {
                    xmlHeadElmNameFinished = true;
                    continue;
                }
                continue;
            }
            if (xmlHeadElmNameFinished && isDelimiter(cur)) {
                if (!newAttrNameStarted && !newAttrValueStarted) {
                    newAttrNameStarted = true;
                    newAttrNameStart = i + 1;
                } else if (newAttrNameStarted) {
                    newAttrNameStarted = false;
                    newAttrNameEnd = i - 1;
                }
                continue;
            }

            if (xmlHeadElmStarted && xmlHeadElmNameFinished) {
                if (newAttrNameStarted) {
                    if (curAttrName == null) {
                        curAttrName = new StringBuilder();
                    }
                    if (validAttrNameChar(cur)) {
                        curAttrName.append(cur);
                    } else if (isQuote(cur)) {
                        newAttrValueStarted = true;
                        newAttrValueStart = i + 1;
                        newAttrNameStarted = false;
                    }
                } else if (newAttrValueStarted) {
                    if (curAttrValue == null) {
                        curAttrValue = new StringBuilder();
                    }
                    if (isQuote(cur)) {
                        newAttrValueStarted = false;
                        newAttrValueEnd = i - 1;
                        root.getXmlHead().addAttribute(
                            new Attribute(
                                curAttrName.toString(),
                                curAttrValue.toString(),
                                newAttrNameStart,
                                newAttrNameEnd,
                                newAttrValueStart,
                                newAttrValueEnd
                            )
                        );
                        curAttrName = new StringBuilder();
                        curAttrValue = new StringBuilder();
                    } else {
                        curAttrValue.append(cur);
                    }
                }
            }

        }
        return root;
    }

    private boolean isDelimiter(char c) {
        final char[] delimiters = {' ', '\n', '\r', '\t'};
        for (int i = 0; i < delimiters.length; i++) {
            if (delimiters[i] == c) {
                return true;
            }
        }
        return false;
    }

    private boolean validAttrNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == '.';
    }

    private boolean validAttrValueChar(char c) {
        return Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == '.';
    }

    private boolean isQuote(char c) {
        return c == '\'' || c == '\"';
    }

    private boolean isXMLChar(char c) {
        return c == 'x' || c == 'm' || c == 'l';
    }
}
