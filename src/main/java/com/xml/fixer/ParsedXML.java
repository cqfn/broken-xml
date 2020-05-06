package com.xml.fixer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParsedXML {

    private final String xml;

    public ParsedXML(final String xml) {
        this.xml = xml;
    }

    public XmlDocument value() throws NoSuchFieldException, IllegalAccessException {

        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        final char[] chars = (char[]) field.get(xml);
        final int len = chars.length;
        assert len == xml.length();

        boolean headElmIsInProcess = false;
        XmlHeadElement curHeadElm = null;

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

        boolean elmTextIsInProcess = false;

        int numberOfOpenBrackets = 0;
        int numberOfClosedBrackets = 0;

        boolean curElmIsInProcess = false;
        Element curElm = null;
        Element curParentElm = null;

        Stack<Element> processingElms = new Stack<>();

        boolean openElmNameIsInProcess = false;
        StringBuilder curOpenElmName = null;

        boolean closeElmNameIsInProcess = false;
        StringBuilder curCloseElmName = null;

        final XmlDocument doc = new XmlDocument(0, len);

        for (int i = 0; i < len; i++) {
            final char cur = chars[i];

            if (cur == '<' && i < len - 1 && chars[i + 1] == '?') {
                continue;
            }

            if (cur == '?' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '?') {
                continue;
            }

            if (cur == '<' && i < len - 1 && chars[i + 1] == '!') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '-') {
                continue;
            }

            if (cur == '<') {
                if (!attributeValueIsInProcess && i < len - 1) {
                    if (chars[i + 1] != '/') {
                        numberOfOpenBrackets += 1;
                        if (!openElmNameIsInProcess) {
                            openElmNameIsInProcess = true;
                            curElm = new Element();
                            curOpenElmName = new StringBuilder();
                            curElm.setStart(i);
                            if (processingElms.size() == 0) {
                                curParentElm = curElm;
                            } else {
                                curParentElm = processingElms.get(processingElms.size() - 1);
                            }
                            processingElms.push(curElm);
                        }
                    } else {
                        numberOfClosedBrackets += 1;
                        if (!closeElmNameIsInProcess) {
                            closeElmNameIsInProcess = true;
                            curCloseElmName = new StringBuilder();
                        }
                    }
                }
                if (elmTextIsInProcess) {
                    elmTextIsInProcess = false;
                }
                continue;
            }

            if (cur == '>') {
                if (i + 1 < len && !elmTextIsInProcess) {
                    elmTextIsInProcess = true;
                }
                if (attributesIsInProcess) {
                    attributesIsInProcess = false;
                    curElm.setAttributes(
                        curAttributes
                    );
                    curAttributes = new ArrayList<>();
                }
                if (openElmNameIsInProcess && curElm != null && curOpenElmName != null) {
                    curElm.setName(curOpenElmName.toString());
                    openElmNameIsInProcess = false;
                    attributesIsInProcess = true;
                    curOpenElmName = null;
                    continue;
                }
                if (closeElmNameIsInProcess && curElm != null && curParentElm != null) {
                    curElm.setEnd(i);
                    if (curParentElm != curElm) {
                        curParentElm.addChild(curElm);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        doc.addRoot(curParentElm);
                        curParentElm = null;
                    } else {
                        curElm = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            curParentElm = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closeElmNameIsInProcess = false;
                    curCloseElmName = null;
                    continue;
                }
                continue;
            }

            if (cur == '/' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '?' && i < len - 1 && chars[i + 1] == '>') {
                if (headElmIsInProcess) {
                    curHeadElm.setEnd(i + 1);
                    doc.setHead(curHeadElm);
                    if (attributesIsInProcess) {
                        attributesIsInProcess = false;
                        doc.getHead().setAttributes(
                            curAttributes
                        );
                        curAttributes = new ArrayList<>();
                    }
                    headElmIsInProcess = false;
                    curHeadElm = null;
                }
                continue;
            }

            if (cur == 'l' && i > 3 && chars[i - 1] == 'm' && chars[i - 2] == 'x' && chars[i - 3] == '?' && chars[i - 4] == '<') {
                headElmIsInProcess = true;
                curHeadElm = new XmlHeadElement();
                curHeadElm.setStart(i - 4);
                continue;
            }

            if (cur == 'm' && i > 2 && i < len - 1 && chars[i + 1] == 'l' && chars[i - 1] == 'x' && chars[i - 2] == '?' && chars[i - 3] == '<') {
                continue;
            }

            if (cur == 'x' && i > 1 && i < len - 2 && chars[i + 1] == 'm' && chars[i + 2] == 'l' && chars[i - 1] == '?' && chars[i - 2] == '<') {
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

            if (isDelimiter(cur)) {
                if (headElmIsInProcess) {
                    attributesIsInProcess = true;
                    continue;
                }
                if (commentIsInProcess) {
                    curComment.append(cur);
                    continue;
                }
                if (openElmNameIsInProcess && curElm != null && curOpenElmName != null) {
                    curElm.setName(curOpenElmName.toString());
                    openElmNameIsInProcess = false;
                    attributesIsInProcess = true;
                    curOpenElmName = null;
                    continue;
                }
                if (closeElmNameIsInProcess && curElm != null && curParentElm != null) {
                    curElm.setEnd(i);
                    if (curParentElm != curElm) {
                        curParentElm.addChild(curElm);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        doc.addRoot(curParentElm);
                        curParentElm = null;
                    } else {
                        curElm = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            curParentElm = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closeElmNameIsInProcess = false;
                    curCloseElmName = null;
                    continue;
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
                    continue;
                }
                if (attributesIsInProcess) {
                    if (attributeNameIsInProcess) {
                        attributeNameIsInProcess = false;
                        curAttributeNameEnd = i - 1;
                    }
                    continue;
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

            if (commentIsInProcess) {
                curComment.append(cur);
                continue;
            }

            if (openElmNameIsInProcess) {
                curOpenElmName.append(cur);
                continue;
            }

            if (closeElmNameIsInProcess) {
                curCloseElmName.append(cur);
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
