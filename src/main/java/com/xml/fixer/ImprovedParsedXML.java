package com.xml.fixer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

        boolean headElmIsInProcess = false;
        XmlHeadElement curHeadElement = null;

        boolean attributesIsInProcess = false;
        List<Attribute> curAttributes = new ArrayList<>();

        boolean attributeNameIsInProcess = false;
        StringBuilder curAttributeName = null;
        int curAttributeNameStart = 0;
        int curAttributeNameEnd = 0;

        boolean attributeValueIsInProcess = false;
        StringBuilder curAttributeValue = null;
        int curAttributeValueStart = 0;
        int curAttributeValueEnd = 0;

        boolean commentIsInProcess = false;
        StringBuilder curComment = null;
        int curCommentStart = 0;
        int curCommentEnd = 0;

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
                if (headElmIsInProcess) {
                    curHeadElement.setEnd(i + 1);
                    doc.setHead(curHeadElement);
                    if (attributesIsInProcess) {
                        attributesIsInProcess = false;
                        doc.getHead().setAttributes(
                            curAttributes
                        );
                        curAttributes = new ArrayList<>();
                    }
                    headElmIsInProcess = false;
                    curHeadElement = null;
                }
                continue;
            }

            if (cur == 'l' && i > 3 && chars[i - 1] == 'm' && chars[i - 2] == 'x' && chars[i - 3] == '?' && chars[i - 4] == '<') {
                headElmIsInProcess = true;
                curHeadElement = new XmlHeadElement();
                curHeadElement.setStart(i - 4);
                continue;
            }

            if (cur == 'm' && i > 2 && i < len - 1 && chars[i + 1] == 'l' && chars[i - 1] == 'x' && chars[i - 2] == '?' && chars[i - 3] == '<') {
                continue;
            }

            if (cur == 'x' && i > 1 && i < len - 2 && chars[i + 1] == 'm' && chars[i + 2] == 'l' && chars[i - 1] == '?' && chars[i - 2] == '<') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '-') {
                continue;
            }

            if (cur == '-' && i < len - 1 && chars[i + 1] == '>') {
                continue;
            }

            if (cur == '-' && i > 2 && chars[i - 1] == '-' && chars[i - 2] == '!' && chars[i - 3] == '<') {
                if (!commentIsInProcess) {
                    commentIsInProcess = true;
                    curComment = new StringBuilder();
                    curCommentStart = i - 3;
                }
                continue;
            }

            if (cur == '-' && i < len - 2 && chars[i + 1] == '-' && chars[i + 2] == '>') {
                if (commentIsInProcess) {
                    curCommentEnd = i + 2;
                    doc.addComment(
                        new Comment(
                            curComment.toString(),
                            curCommentStart,
                            curCommentEnd
                        )
                    );
                    commentIsInProcess = false;
                    curComment = null;
                    curCommentStart = 0;
                    curCommentEnd = 0;
                    continue;
                }
                continue;
            }

            if (cur == '<') {
                continue;
            }

            if (cur == '>') {
                continue;
            }

            if (isDelimiter(cur)) {
                if (headElmIsInProcess) {
                    attributesIsInProcess = true;
                }
                if (commentIsInProcess) {
                    curComment.append(cur);
                }
                continue;
            }

            if (cur == '=') {
                if (headElmIsInProcess) {
                    if (attributesIsInProcess) {
                        if (attributeNameIsInProcess) {
                            attributeNameIsInProcess = false;
                            curAttributeNameEnd = i - 1;
                        }
                    }
                }
                continue;
            }

            if (isQuote(cur)) {
                if (headElmIsInProcess) {
                    if (attributesIsInProcess) {
                        if (!attributeNameIsInProcess && !attributeValueIsInProcess) {
                            attributeValueIsInProcess = true;
                            curAttributeValue = new StringBuilder();
                            curAttributeValueStart = i + 1;
                            continue;
                        }
                        if (attributeValueIsInProcess) {
                            attributeValueIsInProcess = false;
                            curAttributeValueEnd = i - 1;
                            curAttributes.add(
                                new Attribute(
                                    curAttributeName.toString(),
                                    curAttributeValue.toString(),
                                    curAttributeNameStart,
                                    curAttributeNameEnd,
                                    curAttributeValueStart,
                                    curAttributeValueEnd
                                )
                            );
                            curAttributeName = null;
                            curAttributeValue = null;
                            curAttributeNameStart = 0;
                            curAttributeNameEnd = 0;
                            curAttributeValueStart = 0;
                            curAttributeValueEnd = 0;
                            continue;
                        }
                    }
                }
                continue;
            }

            if (headElmIsInProcess) {
                if (attributesIsInProcess) {
                    if (!attributeNameIsInProcess && !attributeValueIsInProcess) {
                        attributeNameIsInProcess = true;
                        curAttributeName = new StringBuilder();
                        curAttributeNameStart = i;
                        curAttributeName.append(cur);
                        continue;
                    }
                    if (attributeNameIsInProcess && !attributeValueIsInProcess) {
                        curAttributeName.append(cur);
                        continue;
                    }
                    if (attributeValueIsInProcess) {
                        curAttributeValue.append(cur);
                        continue;
                    }
                }
                continue;
            }

            if (commentIsInProcess) {
                curComment.append(cur);
                continue;
            }
        }
        return doc;
    }

    private boolean isDelimiter(char c) {
        final char[] delimiters = {' ', '\n', '\r', '\t'};
        for (final char delimiter : delimiters) {
            if (delimiter == c) {
                return true;
            }
        }
        return false;
    }

    private boolean isQuote(char c) {
        return c == '\'' || c == '\"';
    }

    private boolean isXMLChar(char c) {
        return c == 'x' || c == 'm' || c == 'l';
    }

    private boolean isValidAttrNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '.';
    }
}
