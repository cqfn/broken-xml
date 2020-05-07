package com.guseyn.broken_xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class ParsedXML {

    private final String xml;

    public ParsedXML(final String xml) {
        this.xml = xml;
    }

    public XmlDocument value() {

        final int inputLength = xml.length();
        final char[] chars = xml.toCharArray();

        boolean headElementIsInProcess = false;
        XmlHeadElement currentHeadElement = null;
        int currentHeadElementStart = 0;
        int currentHeadElementEnd = 0;

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
        StringBuilder currentCommentText = null;
        int currentCommentStart = 0;
        int currentCommentEnd = 0;

        boolean elementTextIsInProcess = false;
        StringBuilder currentElementText = null;
        int currentElementTextStart = 0;
        int currentElementTextEnd = 0;

        int numberOfOpenBrackets = 0;
        int numberOfClosedBrackets = 0;

        Element currentElement = null;
        Element currentParentElement = null;

        int currentElementStart = 0;

        Stack<Element> processingElms = new Stack<>();

        boolean openingElementNameIsInProcess = false;
        StringBuilder currentOpeningElementName = null;

        boolean closingElementNameIsInProcess = false;
        StringBuilder currentClosingElementName = null;

        final XmlDocument document = new XmlDocument(0, inputLength);

        for (int i = 0; i < inputLength; i++) {
            final char currentChar = chars[i];

            if (currentChar == '<' && i < inputLength - 1 && chars[i + 1] == '?') {
                continue;
            }

            if (currentChar == '?' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (currentChar == '>' && i > 0 && chars[i - 1] == '?') {
                continue;
            }

            if (currentChar == '<' && i < inputLength - 1 && chars[i + 1] == '!') {
                continue;
            }

            if (currentChar == '>' && i > 0 && chars[i - 1] == '-') {
                continue;
            }


            if (currentChar == '/' && i > 0 && chars[i - 1] == '<') {
                continue;
            }

            if (currentChar == '?' && i < inputLength - 1 && chars[i + 1] == '>') {
                if (headElementIsInProcess) {
                    currentHeadElementEnd = i + 1;
                    currentHeadElement = new XmlHeadElement(
                        currentHeadElementStart,
                        currentHeadElementEnd,
                        currentAttributes
                    );
                    document.getHeads().add(currentHeadElement);
                    if (attributesIsInProcess) {
                        attributesIsInProcess = false;
                        currentAttributes = new ArrayList<>();
                    }
                    headElementIsInProcess = false;
                    currentHeadElement = null;
                    currentHeadElementStart = 0;
                    currentHeadElementEnd = 0;
                }
                continue;
            }

            if (currentChar == 'x' && i > 1 && i < inputLength - 2 && chars[i + 1] == 'm' && chars[i + 2] == 'l' && chars[i - 1] == '?' && chars[i - 2] == '<') {
                continue;
            }

            if (currentChar == 'm' && i > 2 && i < inputLength - 1 && chars[i + 1] == 'l' && chars[i - 1] == 'x' && chars[i - 2] == '?' && chars[i - 3] == '<') {
                continue;
            }

            if (currentChar == 'l' && i > 3 && chars[i - 1] == 'm' && chars[i - 2] == 'x' && chars[i - 3] == '?' && chars[i - 4] == '<') {
                headElementIsInProcess = true;
                currentHeadElementStart = i - 4;
                continue;
            }

            if (currentChar == '-' && i < inputLength - 1 && chars[i + 1] == '>') {
                continue;
            }

            if (currentChar == '-' && i > 2 && chars[i - 1] == '-' && chars[i - 2] == '!' && chars[i - 3] == '<') {
                if (!commentIsInProcess) {
                    commentIsInProcess = true;
                    currentCommentText = new StringBuilder();
                    currentCommentStart = i - 3;
                }
                if (elementTextIsInProcess) {
                    elementTextIsInProcess = false;
                    currentElementText = null;
                    currentElementTextStart = 0;
                    currentElementTextEnd = 0;
                }
                continue;
            }

            if (currentChar == '-' && i < inputLength - 2 && chars[i + 1] == '-' && chars[i + 2] == '>') {
                if (commentIsInProcess) {
                    currentCommentEnd = i + 2;
                    document.getComments().add(
                        new Comment(
                            currentCommentText.toString(),
                            currentCommentStart,
                            currentCommentEnd
                        )
                    );
                    commentIsInProcess = false;
                    currentCommentText = null;
                    currentCommentStart = 0;
                    currentCommentEnd = 0;
                    continue;
                }
                continue;
            }

            if (currentChar == '<') {
                if (elementTextIsInProcess) {
                    currentElementTextEnd = i - 1;
                    if (currentElement != null) {
                        currentElement.getTexts().add(
                            new Text(
                                currentElementText.toString(),
                                currentElementTextStart,
                                currentElementTextEnd
                            )
                        );
                    }
                    elementTextIsInProcess = false;
                    currentElementText = null;
                    currentElementTextStart = 0;
                    currentElementTextEnd = 0;
                }
                if (!attributeValueIsInProcess && i < inputLength - 1) {
                    if (chars[i + 1] != '/') {
                        numberOfOpenBrackets += 1;
                        if (!openingElementNameIsInProcess) {
                            openingElementNameIsInProcess = true;
                            currentOpeningElementName = new StringBuilder();
                            currentElementStart = i;
                        }
                    } else {
                        numberOfClosedBrackets += 1;
                        if (!closingElementNameIsInProcess) {
                            closingElementNameIsInProcess = true;
                            currentClosingElementName = new StringBuilder();
                        }
                    }
                }
                continue;
            }

            if (currentChar == '>') {
                if (i + 1 < inputLength && !elementTextIsInProcess) {
                    elementTextIsInProcess = true;
                    currentElementText = new StringBuilder();
                    currentElementTextStart = i + 1;
                }
                if (currentOpeningElementName != null) {
                    currentElement = new Element(
                        currentOpeningElementName.toString(),
                        currentElementStart
                    );
                    currentElement.getAttributes().addAll(currentAttributes);
                    if (processingElms.size() == 0) {
                        currentParentElement = currentElement;
                    } else {
                        currentParentElement = processingElms.get(processingElms.size() - 1);
                    }
                    processingElms.push(currentElement);
                    openingElementNameIsInProcess = false;
                    currentOpeningElementName = null;
                    if (attributesIsInProcess) {
                        attributesIsInProcess = false;
                        currentAttributes = new ArrayList<>();
                    }
                    continue;
                }
                if (closingElementNameIsInProcess && currentElement != null && currentParentElement != null) {
                    currentElement.correctEnd(i);
                    if (currentParentElement != currentElement) {
                        currentParentElement.getChildren().add(currentElement);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        document.getRoots().add(currentParentElement);
                        currentParentElement = null;
                    } else {
                        currentElement = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            currentParentElement = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closingElementNameIsInProcess = false;
                    currentClosingElementName = null;
                    continue;
                }
                continue;
            }

            if (isDelimiter(currentChar)) {
                if (headElementIsInProcess) {
                    attributesIsInProcess = true;
                    continue;
                }
                if (commentIsInProcess) {
                    currentCommentText.append(currentChar);
                    continue;
                }
                if (elementTextIsInProcess) {
                    currentElementText.append(currentChar);
                    continue;
                }
                if (attributeValueIsInProcess) {
                    currentAttributeValue.append(currentChar);
                    continue;
                }
                if (openingElementNameIsInProcess) {
                    openingElementNameIsInProcess = false;
                    attributesIsInProcess = true;
                    continue;
                }
                if (closingElementNameIsInProcess && currentElement != null && currentParentElement != null) {
                    currentElement.correctEnd(i);
                    if (currentParentElement != currentElement) {
                        currentParentElement.getChildren().add(currentElement);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        document.getRoots().add(currentParentElement);
                        currentParentElement = null;
                    } else {
                        currentElement = processingElms.get(processingElms.size() - 1);
                        if (processingElms.size() > 1) {
                            currentParentElement = processingElms.get(processingElms.size() - 2);
                        }
                    }
                    closingElementNameIsInProcess = false;
                    currentClosingElementName = null;
                    continue;
                }
                continue;
            }

            if (currentChar == '=') {
                if (attributesIsInProcess) {
                    if (attributeNameIsInProcess) {
                        attributeNameIsInProcess = false;
                        currentAttributeNameEnd = i - 1;
                    }
                    continue;
                }
                continue;
            }

            if (isQuote(currentChar)) {
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
                    currentAttributeName.append(currentChar);
                    continue;
                }
                if (attributeNameIsInProcess && !attributeValueIsInProcess) {
                    currentAttributeName.append(currentChar);
                    continue;
                }
                if (attributeValueIsInProcess) {
                    currentAttributeValue.append(currentChar);
                    continue;
                }
            }

            if (commentIsInProcess) {
                currentCommentText.append(currentChar);
                continue;
            }

            if (openingElementNameIsInProcess) {
                currentOpeningElementName.append(currentChar);
                continue;
            }

            if (closingElementNameIsInProcess) {
                currentClosingElementName.append(currentChar);
                continue;
            }

            if (elementTextIsInProcess) {
                currentElementText.append(currentChar);
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
