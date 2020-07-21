package com.guseyn.broken_xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class ParsedXML {

    private final String xml;

    public ParsedXML(final String xml) {
        this.xml = xml;
    }

    public XmlDocument document() {

        final int inputLength = xml.length();
        final char[] chars = xml.toCharArray();

        final List<Element> allElements = new ArrayList<>();

        boolean headElementIsInProcess = false;
        HeadElement currentHeadElement = null;
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
        char currentTypeOfOpeningQuote = '"';

        boolean commentIsInProcess = false;
        StringBuilder currentCommentText = null;
        int currentCommentStart = 0;
        int currentCommentEnd = 0;

        boolean elementTextIsInProcess = false;
        StringBuilder currentElementText = null;
        int currentElementTextStart = 0;
        int currentElementTextEnd = 0;

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
                if (commentIsInProcess) {
                    currentCommentText.append(currentChar);
                }
                if (elementTextIsInProcess) {
                    currentElementText.append(currentChar);
                }
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
                    currentHeadElement = new HeadElement(
                        currentHeadElementStart,
                        currentHeadElementEnd,
                        currentAttributes
                    );
                    document.heads().add(currentHeadElement);
                    attributesIsInProcess = false;
                    currentAttributes = new ArrayList<>();
                    headElementIsInProcess = false;
                    currentHeadElement = null;
                    currentHeadElementStart = 0;
                    currentHeadElementEnd = 0;
                }
                continue;
            }

            if (
                isL(currentChar)
                    && i > 3
                    && isM(chars[i - 1])
                    && isX(chars[i - 2])
                    && chars[i - 3] == '?'
                    && chars[i - 4] == '<'
            ) {
                headElementIsInProcess = true;
                currentHeadElementStart = i - 4;
                continue;
            }

            if (
                currentChar == '-'
                    && i < inputLength - 1
                    && chars[i + 1] == '>'
            ) {
                continue;
            }

            if (
                currentChar == '-'
                    && i > 2
                    && chars[i - 1] == '-'
                    && chars[i - 2] == '!'
                    && chars[i - 3] == '<'
            ) {
                if (!commentIsInProcess) {
                    commentIsInProcess = true;
                    currentCommentText = new StringBuilder();
                    currentCommentStart = i - 3;
                } else {
                    currentCommentText.append(currentChar);
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
                    document.comments().add(
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
                if (commentIsInProcess) {
                    currentCommentText.append(currentChar);
                    continue;
                }
                if (!attributeValueIsInProcess && i < inputLength - 1  && !this.isDelimiter(chars[i + 1]) && !this.isBracket(chars[i + 1])) {
                    if (chars[i + 1] != '/') {
                        if (!openingElementNameIsInProcess) {
                            openingElementNameIsInProcess = true;
                            currentOpeningElementName = new StringBuilder();
                            currentElementStart = i;
                        } else {
                            currentOpeningElementName.append(currentChar);
                        }
                    } else {
                        closingElementNameIsInProcess = true;
                        currentClosingElementName = new StringBuilder();
                    }
                } else {
                    if (attributeValueIsInProcess) {
                        currentAttributeValue.append(currentChar);
                        continue;
                    }
                }
                if (elementTextIsInProcess && !this.isDelimiter(chars[i + 1]) && !this.isBracket(chars[i + 1])) {
                    currentElementTextEnd = i - 1;
                    currentElement.texts().add(
                        new Text(
                            currentElementText.toString(),
                            currentElementTextStart,
                            currentElementTextEnd
                        )
                    );
                    elementTextIsInProcess = false;
                    currentElementText = null;
                    currentElementTextStart = 0;
                    currentElementTextEnd = 0;
                } else if (elementTextIsInProcess) {
                    currentElementText.append(currentChar);
                }
                continue;
            }

            if (currentChar == '>') {
                if (commentIsInProcess) {
                    currentCommentText.append(currentChar);
                    continue;
                }
                if (attributeValueIsInProcess) {
                    currentAttributeValue.append(currentChar);
                    continue;
                }
                if (elementTextIsInProcess) {
                    currentElementText.append(currentChar);
                }
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
                    currentElement.attributes().addAll(currentAttributes);
                    allElements.add(currentElement);
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
                if (closingElementNameIsInProcess && currentElement != null) {
                    if (currentElement.name().equals(currentClosingElementName.toString())) {
                        currentElement.correctEnd(i);
                    } else {
                        currentElement.correctEnd(
                            currentElement.texts().get(
                                currentElement.texts().size() - 1
                            ).end()
                        );
                    }
                    if (currentParentElement != currentElement) {
                        currentParentElement.children().add(currentElement);
                    }
                    processingElms.pop();
                    if (processingElms.size() == 0) {
                        document.roots().add(currentParentElement);
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
                continue;
            }

            if (currentChar == '=') {
                if (attributeNameIsInProcess) {
                    attributeNameIsInProcess = false;
                    currentAttributeNameEnd = i - 1;
                }
                continue;
            }

            if (isQuote(currentChar)) {
                if (!attributeValueIsInProcess && currentAttributeName != null) {
                    attributeValueIsInProcess = true;
                    currentTypeOfOpeningQuote = currentChar;
                    currentAttributeValue = new StringBuilder();
                    currentAttributeValueStart = i + 1;
                    continue;
                }
                if (
                    currentAttributeName != null
                        && currentChar == currentTypeOfOpeningQuote
                        && (isDelimiter(chars[i + 1]) || chars[i + 1] == '>' || chars[i + 1] == '?')
                ) {
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
                } else if (currentAttributeValue != null) {
                    currentAttributeValue.append(currentChar);
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
                if (attributeNameIsInProcess) {
                    currentAttributeName.append(currentChar);
                    continue;
                }
                currentAttributeValue.append(currentChar);
                continue;
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

        if (processingElms.size() > 0) {
            if (elementTextIsInProcess) {
                processingElms.get(processingElms.size() - 1).texts().add(
                    new Text(
                        currentElementText.toString(),
                        currentElementTextStart,
                        currentElementTextEnd
                    )
                );
            }
        }

        if (commentIsInProcess) {
            document.comments().add(
                new Comment(
                    currentCommentText.toString(),
                    currentCommentStart,
                    document.end()
                )
            );
        }

        return traverseUnclosedElements(document, processingElms, allElements);
    }

    private boolean isDelimiter(final char c) {
        final char[] delimiters = {' ', '\n', '\r', '\t'};
        for (final char delimiter : delimiters) {
            if (delimiter == c) {
                return true;
            }
        }
        return false;
    }

    private boolean isBracket(final char c) {
        return c == '<' || c == '>';
    }

    private boolean isQuote(final char c) {
        return c == '\'' || c == '\"';
    }

    private XmlDocument traverseUnclosedElements(
        final XmlDocument document,
        final Stack<Element> unclosedElms,
        final List<Element> allElements
    ) {
        if (unclosedElms.size() > 0) {
            Element lastChild = unclosedElementWithCorrectedEnd(
                unclosedElms.pop(),
                allElements
            );
            final Element parentOfLastChild = this.parentOfUnclosedElement(
                lastChild, allElements
            );
            if (parentOfLastChild != null) {
                parentOfLastChild.children().add(lastChild);
            } else if (document.roots().size() > 0) {
                document.roots().get(document.roots().size() - 1).children().add(lastChild);
            } else {
                document.roots().add(lastChild);
            }
            allElements.add(lastChild);
            traverseUnclosedElements(document, unclosedElms, allElements);
        }
        return document;
    }

    private Element unclosedElementWithCorrectedEnd(
        final Element unclosedElement,
        final List<Element> allElements
    ) {
        for (Element element: allElements) {
            if (element.start() > unclosedElement.start()) {
                unclosedElement.correctEnd(element.start());
                break;
            }
        }
        if (unclosedElement.texts().size() > 0) {
            unclosedElement.correctEnd(
                unclosedElement.texts().get(
                    unclosedElement.texts().size() - 1
                ).end()
            );
        }
        return unclosedElement;
    }

    private Element parentOfUnclosedElement(
        final Element unclosedElement,
        final List<Element> allElements
    ) {
        for (int i = allElements.size() - 1; i >= 0; i--) {
            final Element currentElement = allElements.get(i);
            if (currentElement.start() < unclosedElement.start() && currentElement.end() == 0) {
                return allElements.get(i);
            }
        }
        return null;
    }

    private boolean isX(final char c) {
        return c == 'x' || c == 'X';
    }

    private boolean isM(final char c) {
        return c == 'm' || c == 'M';
    }

    private boolean isL(final char c) {
        return c == 'l' || c == 'L';
    }
}
