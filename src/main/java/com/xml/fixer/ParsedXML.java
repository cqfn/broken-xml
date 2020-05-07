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
        final int inputLength = chars.length;
        assert inputLength == xml.length();

        boolean headElementIsInProcess = false;
        XmlHeadElement currentHeadElement = null;

        boolean attributesIsInProcess = false;
        List<Attribute> currentAttributes = new ArrayList<>();

        boolean attributeNameIsInProcess = false;
        StringBuilder currentAttributeName = null;
        int currentAttributeNameStart = 0;
        int currentAttributeNameEnd = 0;

        boolean attributeValueIsInProcess = false;
        StringBuilder currentAttributeValue = null;
        int currentAttributeValueStart = 0;
        int currentAttributeValueEnd = 0;

        boolean commentIsInProcess = false;
        StringBuilder curComment = null;
        int currentCommentStart = 0;
        int currentCommentEnd = 0;

        boolean elementTextIsInProcess = false;

        int numberOfOpenBrackets = 0;
        int numberOfClosedBrackets = 0;

        Element currentElm = null;
        Element currentParentElm = null;

        Stack<Element> processingElms = new Stack<>();

        boolean openingElementNameIsInProcess = false;
        StringBuilder currentOpeningElememtName = null;

        boolean closingElementNameIsInProcess = false;
        StringBuilder currentClosingElementName = null;

        final XmlDocument document = new XmlDocument(0, inputLength);

        for (int i = 0; i < inputLength; i++) {
            final char cur = chars[i];

            if (cur == '<' && i < inputLength - 1 && chars[i + 1] == '?') {
                continue;
            }

            if (cur == '?' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '?') {
                continue;
            }

            if (cur == '<' && i < inputLength - 1 && chars[i + 1] == '!') {
                continue;
            }

            if (cur == '>' && i > 0 && chars[i - 1] == '-') {
                continue;
            }

            if (cur == '<') {
                if (!attributeValueIsInProcess && i < inputLength - 1) {
                    if (chars[i + 1] != '/') {
                        numberOfOpenBrackets += 1;
                        if (!openingElementNameIsInProcess) {
                            openingElementNameIsInProcess = true;
                            currentElm = new Element();
                            currentOpeningElememtName = new StringBuilder();
                            currentElm.setStart(i);
                            if (processingElms.size() == 0) {
                                currentParentElm = currentElm;
                            } else {
                                currentParentElm = processingElms.get(processingElms.size() - 1);
                            }
                            processingElms.push(currentElm);
                        }
                    } else {
                        numberOfClosedBrackets += 1;
                        if (!closingElementNameIsInProcess) {
                            closingElementNameIsInProcess = true;
                            currentClosingElementName = new StringBuilder();
                        }
                    }
                }
                if (elementTextIsInProcess) {
                    elementTextIsInProcess = false;
                }
                continue;
            }

            if (cur == '>') {
                if (i + 1 < inputLength && !elementTextIsInProcess) {
                    elementTextIsInProcess = true;
                }
                if (attributesIsInProcess) {
                    attributesIsInProcess = false;
                    currentElm.setAttributes(
                        currentAttributes
                    );
                    currentAttributes = new ArrayList<>();
                }
                if (openingElementNameIsInProcess && currentElm != null && currentOpeningElememtName != null) {
                    currentElm.setName(currentOpeningElememtName.toString());
                    openingElementNameIsInProcess = false;
                    currentOpeningElememtName = null;
                    continue;
                }
                if (closingElementNameIsInProcess && currentElm != null && currentParentElm != null) {
                    currentElm.setEnd(i);
                    if (currentParentElm != currentElm) {
                        currentParentElm.addChild(currentElm);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        document.addRoot(currentParentElm);
                        currentParentElm = null;
                    } else {
                        currentElm = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            currentParentElm = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closingElementNameIsInProcess = false;
                    currentClosingElementName = null;
                    continue;
                }
                continue;
            }

            if (cur == '/' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (cur == '?' && i < inputLength - 1 && chars[i + 1] == '>') {
                if (headElementIsInProcess) {
                    currentHeadElement.setEnd(i + 1);
                    document.setHead(currentHeadElement);
                    if (attributesIsInProcess) {
                        attributesIsInProcess = false;
                        document.getHead().setAttributes(
                            currentAttributes
                        );
                        currentAttributes = new ArrayList<>();
                    }
                    headElementIsInProcess = false;
                    currentHeadElement = null;
                }
                continue;
            }

            if (cur == 'l' && i > 3 && chars[i - 1] == 'm' && chars[i - 2] == 'x' && chars[i - 3] == '?' && chars[i - 4] == '<') {
                headElementIsInProcess = true;
                currentHeadElement = new XmlHeadElement();
                currentHeadElement.setStart(i - 4);
                continue;
            }

            if (cur == 'm' && i > 2 && i < inputLength - 1 && chars[i + 1] == 'l' && chars[i - 1] == 'x' && chars[i - 2] == '?' && chars[i - 3] == '<') {
                continue;
            }

            if (cur == 'x' && i > 1 && i < inputLength - 2 && chars[i + 1] == 'm' && chars[i + 2] == 'l' && chars[i - 1] == '?' && chars[i - 2] == '<') {
                continue;
            }

            if (cur == '-' && i < inputLength - 1 && chars[i + 1] == '>') {
                continue;
            }

            if (cur == '-' && i > 2 && chars[i - 1] == '-' && chars[i - 2] == '!' && chars[i - 3] == '<') {
                if (!commentIsInProcess) {
                    commentIsInProcess = true;
                    curComment = new StringBuilder();
                    currentCommentStart = i - 3;
                }
                continue;
            }

            if (cur == '-' && i < inputLength - 2 && chars[i + 1] == '-' && chars[i + 2] == '>') {
                if (commentIsInProcess) {
                    currentCommentEnd = i + 2;
                    document.addComment(
                        new Comment(
                            curComment.toString(),
                            currentCommentStart,
                            currentCommentEnd
                        )
                    );
                    commentIsInProcess = false;
                    curComment = null;
                    currentCommentStart = 0;
                    currentCommentEnd = 0;
                    continue;
                }
                continue;
            }

            if (isDelimiter(cur)) {
                if (headElementIsInProcess) {
                    attributesIsInProcess = true;
                    continue;
                }
                if (commentIsInProcess) {
                    curComment.append(cur);
                    continue;
                }
                if (openingElementNameIsInProcess && currentElm != null && currentOpeningElememtName != null) {
                    currentElm.setName(currentOpeningElememtName.toString());
                    openingElementNameIsInProcess = false;
                    attributesIsInProcess = true;
                    currentOpeningElememtName = null;
                    continue;
                }
                if (closingElementNameIsInProcess && currentElm != null && currentParentElm != null) {
                    currentElm.setEnd(i);
                    if (currentParentElm != currentElm) {
                        currentParentElm.addChild(currentElm);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        document.addRoot(currentParentElm);
                        currentParentElm = null;
                    } else {
                        currentElm = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            currentParentElm = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closingElementNameIsInProcess = false;
                    currentClosingElementName = null;
                    continue;
                }
                continue;
            }

            if (cur == '=') {
                if (attributesIsInProcess) {
                    if (attributeNameIsInProcess) {
                        attributeNameIsInProcess = false;
                        currentAttributeNameEnd = i - 1;
                    }
                    continue;
                }
                continue;
            }

            if (isQuote(cur)) {
                if (attributesIsInProcess) {
                    if (!attributeNameIsInProcess && !attributeValueIsInProcess) {
                        attributeValueIsInProcess = true;
                        currentAttributeValue = new StringBuilder();
                        currentAttributeValueStart = i + 1;
                        continue;
                    }
                    if (attributeValueIsInProcess) {
                        attributeValueIsInProcess = false;
                        currentAttributeValueEnd = i - 1;
                        currentAttributes.add(
                            new Attribute(
                                currentAttributeName.toString(),
                                currentAttributeValue.toString(),
                                currentAttributeNameStart,
                                currentAttributeNameEnd,
                                currentAttributeValueStart,
                                currentAttributeValueEnd
                            )
                        );
                        currentAttributeName = null;
                        currentAttributeValue = null;
                        currentAttributeNameStart = 0;
                        currentAttributeNameEnd = 0;
                        currentAttributeValueStart = 0;
                        currentAttributeValueEnd = 0;
                        continue;
                    }
                }
                continue;
            }

            if (attributesIsInProcess) {
                if (!attributeNameIsInProcess && !attributeValueIsInProcess) {
                    attributeNameIsInProcess = true;
                    currentAttributeName = new StringBuilder();
                    currentAttributeNameStart = i;
                    currentAttributeName.append(cur);
                    continue;
                }
                if (attributeNameIsInProcess && !attributeValueIsInProcess) {
                    currentAttributeName.append(cur);
                    continue;
                }
                if (attributeValueIsInProcess) {
                    currentAttributeValue.append(cur);
                    continue;
                }
            }

            if (commentIsInProcess) {
                curComment.append(cur);
                continue;
            }

            if (openingElementNameIsInProcess) {
                currentOpeningElememtName.append(cur);
                continue;
            }

            if (closingElementNameIsInProcess) {
                currentClosingElementName.append(cur);
                continue;
            }
        }
        return document;
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
}
