package com.xml.fixer;

import java.lang.reflect.Field;
import java.util.Stack;

public final class ParsedXml {

    private final String xml;

    public ParsedXml(final String xml) {
        this.xml = xml;
    }

    public XmlDocument value() throws NoSuchFieldException, IllegalAccessException {

        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        final char[] chars = (char[]) field.get(xml);
        final int len = chars.length;
        assert len == xml.length();

        final Stack<Element> elements = new Stack<>();
        Element curElm = null;
        boolean nextCharIsEscaped = false;

        boolean headElmIsInProcess = false;
        boolean headAttrsIsInProcess = false;
        boolean newElmIsInProcess = false;
        long newElmStart = 0;
        long newElmEnd = 0;
        boolean newElmNameIsInProcess = false;
        long newElmNameStart = 0;
        long newElmNameEnd = 0;
        boolean newAttrNameIsInProcess = false;
        long newAttrNameStart = 0;
        long newAttrNameEnd = 0;
        boolean newAttrValueIsInProcess = false;
        long newAttrValueStart = 0;
        long newAttrValueEnd = 0;
        boolean newCommentIsInProcess = false;
        boolean newCommentTextIsInProcess = false;
        long newCommentStart = 0;
        long newCommentEnd = 0;
        boolean newTextIsInProcess = false;
        StringBuilder curElmName = null;
        StringBuilder curAttrName = null;
        StringBuilder curAttrValue = null;
        StringBuilder curElmText = null;
        StringBuilder curCommentText = null;
        final XmlDocument doc = new XmlDocument(0, len);

        for (int i = 0; i < len; i++) {
            final char cur = chars[i];

            if (cur == '<' && !nextCharIsEscaped) {
                newElmIsInProcess = true;
                continue;
            }

            if (cur == '>' && !nextCharIsEscaped) {
                if (headElmIsInProcess) {
                    newElmIsInProcess = false;
                    headElmIsInProcess = false;
                    doc.getHead().setEnd(i);
                    continue;
                }
                if (newCommentIsInProcess) {
                    if (curCommentText != null) {
                        doc.addComment(
                            new Comment(
                                newCommentStart,
                                newCommentEnd,
                                curCommentText.toString()
                            )
                        );
                        newCommentIsInProcess = false;
                        curCommentText = null;
                    }
                    continue;
                }
            }

            if (cur == '?' && !nextCharIsEscaped) {
                if (newElmIsInProcess && !newAttrNameIsInProcess && !newAttrValueIsInProcess) {
                    if (headElmIsInProcess) {
                        headAttrsIsInProcess = false;
                        continue;
                    }
                    headElmIsInProcess = true;
                    if (!doc.hasHead()) {
                        XmlHeadElement head = new XmlHeadElement();
                        head.setStart(i - 1);
                        doc.addHead(head);
                    }
                    continue;
                }
            }

            if (cur == '!' && !nextCharIsEscaped) {
                if (newElmIsInProcess && !newAttrNameIsInProcess && !newAttrValueIsInProcess) {
                    if (!newCommentIsInProcess) {
                        newCommentIsInProcess = true;
                        newCommentStart = i;
                        continue;
                    }
                }
            }

            if (cur == '-' && !nextCharIsEscaped) {
                if (newElmIsInProcess && !newAttrNameIsInProcess && !newAttrValueIsInProcess) {
                    if (newCommentIsInProcess) {
                        if (!newCommentTextIsInProcess && i > 1 && chars[i - 1] == '-') {
                            if (curCommentText == null) {
                                curCommentText = new StringBuilder();
                            }
                            newCommentTextIsInProcess = true;
                            continue;
                        }
                        if (newCommentTextIsInProcess && i > 1 && chars[i + 1] == '>') {
                            newCommentEnd = i + 1;
                            newCommentTextIsInProcess = false;
                            continue;
                        }
                    }
                }
            }

            if (isDelimiter(cur)) {
                if (newElmIsInProcess && !newAttrNameIsInProcess && !newAttrValueIsInProcess) {
                    if (headElmIsInProcess) {
                        if (headAttrsIsInProcess) {
                            newAttrNameIsInProcess = true;
                            newAttrNameStart = i + 1;
                        }
                        continue;
                    }
                }
            }

            if (newElmIsInProcess && !newAttrNameIsInProcess && !newAttrValueIsInProcess) {
                if (newCommentIsInProcess && newCommentTextIsInProcess) {
                    if (curCommentText != null && isValidCommentText(cur, nextCharIsEscaped)) {
                        curCommentText.append(cur);
                    }
                    continue;
                }
            }

            if (headElmIsInProcess &&
                isXMLChar(cur) &&
                !newAttrNameIsInProcess &&
                !newAttrValueIsInProcess) {
                if (cur == 'x') {
                    headAttrsIsInProcess = false;
                    continue;
                }
                if (cur == 'l') {
                    headAttrsIsInProcess = true;
                    continue;
                }
                continue;
            }

            if (headElmIsInProcess && headAttrsIsInProcess) {
                if (newAttrNameIsInProcess) {
                    if (curAttrName == null) {
                        curAttrName = new StringBuilder();
                    }
                    if (isValidAttrNameChar(cur)) {
                        curAttrName.append(cur);
                    } else if (isQuote(cur)) {
                        newAttrValueIsInProcess = true;
                        newAttrNameEnd = i - 1;
                        newAttrValueStart = i + 1;
                        newAttrNameIsInProcess = false;
                    }
                } else if (newAttrValueIsInProcess) {
                    if (curAttrValue == null) {
                        curAttrValue = new StringBuilder();
                    }
                    if (isQuote(cur)) {
                        newAttrValueIsInProcess = false;
                        newAttrValueEnd = i - 1;
                        doc.getHead().addAttribute(
                            new Attribute(
                                curAttrName.toString(),
                                curAttrValue.toString(),
                                newAttrNameStart,
                                newAttrNameEnd,
                                newAttrValueStart,
                                newAttrValueEnd
                            )
                        );
                        curAttrName = null;
                        curAttrValue = null;
                    } else {
                        curAttrValue.append(cur);
                    }
                }
            }
        }
        return doc;
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

    private boolean isValidAttrNameChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '.';
    }

    private boolean isValidCommentText(char c, boolean charIsEscaped) {
        if (charIsEscaped) {
            return true;
        }
        return c != '-';
    }

    private boolean isQuote(char c) {
        return c == '\'' || c == '\"';
    }

    private boolean isXMLChar(char c) {
        return c == 'x' || c == 'm' || c == 'l';
    }
}
