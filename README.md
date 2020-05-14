# Broken XML

<img src="https://raw.githubusercontent.com/Guseyn/logos/master/broken-xml.svg?sanitize=true">

[![Maven Central](https://img.shields.io/maven-central/v/com.guseyn.broken-xml/broken-xml.svg)](https://maven-badges.herokuapp.com/maven-central/com.guseyn.broken-xml/broken-xml)
[![Build Status](https://travis-ci.com/Guseyn/broken-xml.svg?branch=master)](https://travis-ci.com/Guseyn/broken-xml)
[![codecov](https://codecov.io/gh/Guseyn/broken-xml/branch/master/graph/badge.svg)](https://codecov.io/gh/Guseyn/broken-xml)

**Broken XML** is a parser that can parse any broken and invalid xml. This parser should not be used
by any normal human being. But if you're lucky like myself, just read further...

## Add via Maven

```xml
<dependency>
  <groupId>com.guseyn.broken-xml</groupId>
  <artifactId>broken-xml</artifactId>
  <version>${broken-xml.last-version}</version>
</dependency>
```

## Build from sources

```bash
mvn clean package -Plocal
```

Jar file is `/target/broken-xml-<version>.jar`.

Or you can just install last version of jar file in the [**releases**](https://github.com/Guseyn/broken-xml/releases) section.

## API

**Broken XML** works only with a simple `String` on input:

```java
public class Main {
    static void main(String[] args){ 
        XmlDocument document = new ParsedXML("<xml>...</xml>").document();
        // You can get list of head elements, if for some reason you have several of them
        List<HeadElement> heads = document.heads(); 
        // You can get multiple roots
        List<Element> roots = document.roots();
        // You can even get comments in your xml
        List<Comment> comments = document.comments();
    }
}
```

### Structure

<details>
  <summary><b>XmlDocument</b></summary><br>
  
  **XmlDocument** is what you get by calling `new ParsedXML(xmlAsString).document()`.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  // Components:
  List<HeadElement> heads = document.heads();
  List<Element> roots = document.roots();
  List<Comment> comments = document.comments();
  int start = document.start(); // is always 0
  int end = document.end(); // is always a length of xml string
```
</details>

<details>
  <summary><b>HeadElement</b></summary><br>
  
  **HeadElement** represents head of xml. It's an element that looks like `<?xml ... ?>`.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  HeadElement head = document.heads().get(0);
  // Components:
  List<Attribute> attributes = head.attributes();
  int start = element.start();
  int end = element.end();
```
</details>

<details>
  <summary><b>Element</b></summary><br>
  
  **Element** can be either a root or just a child node in xml.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  Element element = document.roots().get(0); // can be aslo retrieved from another element via children() method
  // Components:
  String name = element.name();
  List<Attribute> attributes = element.attributes();
  List<Element> children = element.children();
  List<Text> texts = element.texts();
  int start = element.start();
  int end = element.end();
```
</details>


<details>
  <summary><b>Attribute</b></summary><br>
  
  **Attribute** can be either a component of `HeadElement` or `Element`.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  Element element = document.roots().get(0);
  Attribute attribute = element.attributes().get(0); 
  // Components:
  String name = attribute.name();
  String value = attribute.value();
  int nameStart = element.nameStart();
  int nameEnd = element.nameEnd();
  int valueStart = value.nameStart();
  int valueEnd = value.valueEnd();
```
</details>


<details>
  <summary><b>Text</b></summary><br>
  
  **Text** is a component of `Element`.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  HeadElement element = document.heads().get(0);
  Element element = document.roots().get(0)
  Text text = element.texts().get(0) 
  // Components:
  String value = text.value();
  int start = text.start();
  int end = text.end();
```
</details>

<details>
  <summary><b>Comment</b></summary><br>
  
  **Comment** is a component of `XmlDocument`.
  
  ```java
  XmlDocument document = new ParsedXML(xmlAsString).document();
  Comment comment = document.comments().get(0);
  // Components:
  String text = comment.text();
  int start = comment.start();
  int end = comment.end();
```
</details>

## How broken is your XML?

<details>
  <summary><b>Empty xml</b></summary><br>

If you have an empty xml, no problem, you'll get just empty `XmlDocument`:

```java
public class EmptyXmlTest {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML("");
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 0);
        assertEquals(document.roots().size(), 0);
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 0);
    }
}
```

</details>

<details>
  <summary><b>XML that is wrapped with some other text</b></summary><br>


**Broken XML** allows you to have xml text with no xml stuff, in such case it will return information only about xml part:

```java
public class NoXmlAroundXmlTest {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML("Some text here<root attr=\"value\">text</root>and some text here");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "root");
        assertEquals(document.roots().get(0).texts().get(0).value(), "text");
    }
}
```

</details>

<details>
  <summary><b>Multiple roots</b></summary><br>

Valid xml contains only one root element. But **Broken XML** does not care and returns multiple roots as a list:

```java
public class MultipleRootsTest {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML("<root1></root1><root2></root2>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 2);
        assertEquals(document.roots().get(0).name(), "root1");
        assertEquals(document.roots().get(1).name(), "root2");
    }
}
```

</details>

<details>
  <summary><b>Duplicate attributes in elements</b></summary><br>

It does not matter anymore if elements in your xml have duplicate attribute names, **Broken XML** will return a list of them:

```java
public class DuplicateAttributesInElementTest {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML("<elm attr=\"value1\" attr=\"value2\"></elm>");
        XmlDocument document = xml.document();
        Element element = document.roots().get(0);
        assertEquals(element.attributes().size(), 2);
        assertEquals(element.attributes().get(0).name(), "attr");
        assertEquals(element.attributes().get(0).value(), "value1");
        assertEquals(element.attributes().get(1).name(), "attr");
        assertEquals(element.attributes().get(1).value(), "value2");
    }
}
```

</details>

<details>
  <summary><b>Some tags are not closed</b></summary><br>
  
You can have xml with unclosed tags:

```xml
<root>
  <elm1 attr="value">
    text
  </elm1>
  <elm2 attr="value" attr="value">text
</root>
```

That's fine, **Broken xml** parses such things:

```java
public class SomeTagsAreNotClosedTest {
    @Test
    void test() {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).children().size(), 2);
        assertEquals(document.roots().get(0).children().get(1).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(1).texts().get(0).value(), "text\n");
        assertEquals(document.roots().get(0).children().get(1).texts().get(0).end(), 86);
        assertEquals(document.roots().get(0).children().get(1).end(), 86);
    }
}
```

</details>

<details>
  <summary><b>No closing tags at all</b></summary><br>
  
Who needs closing tags anyway, right?

```xml
<root>
  <elm1 attr="value" attr="value">
    <elm2 attr="value" attr="value">
      <elm3 attr="value" attr="value">
        <elm4 attr="value" attr="value">
          <elm5 attr="value" attr="value">
            <elm6 attr="value" attr="value">text
```

That's fine, **Broken xml** parses even such things:

```java
public class NoClosingTagsAtAllTest {
    @Test
    void test() {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).name(), "elm1");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).name(), "elm3");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm4");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm5");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm6");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
```

</details>

<details>
  <summary><b>Swapped opening and closing tags</b></summary><br>
  
Obviously **Broken XXML** does not care if names in opening and closing tags of elements match:

```xml
<elm1>
  <elm2>text</elm1>
</elm2>
```

**Broken XML** can easily eat such stuff:

```java
public class SwappedOpeningAndClosingTags {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm1");
        assertEquals(document.roots().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
```

</details>

<details>
    <summary><b>Brackets inside of elements</b></summary><br>
    
**Broken XML** can handle brackets `<`, `>` inside of elements if they are not really part of element tags:

```xml
<elm1>
  <><<
  <elm2>
    <><<
  </elm2>
</elm1>
```
    
It will be parsed with no problems:

```java

public class BracketsInTexts extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).name(), "elm1");
        assertEquals(document.roots().get(0).texts().get(0).value(), "\n  <><<\n  ");
        assertEquals(document.roots().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(0).texts().get(0).value(), "\n    <><<\n  ");
    }
}
```
    
</details>

<details>
    <summary><b>Non-escaped quotes in attribute values</b></summary><br>
    
Guess what else **Broken XML** can do. You don't have to escape quotes anymore: 

```xml
<elm attr=""va""lu""e">

</elm>
```
    
It will be parsed with no problems:

```java
public class NonEscapedQuotesTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm");
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "\"va\"\"lu\"\"e");
    }
}
```
 
**Important note**: it works only if non-escaped quotes are not followed by space or `>` symbol (remember it's impossible to read your mind, luckily for you).
 
</details>

## Impossible even for Broken XML

<details>
  <summary><b>Different types of opening and closing quotes for attribute values</b></summary><br>

Sorry, but if you will have something like this:

```xml
<root attr1='value1">
  text1
</root>
<root attr2="value2'>
text2
</root>
```

it will parsed like an element with attribute that has value which is xml-like text:

```java
public class DifferentTypesOfOpeningAndClosingQuotesForAttributeValuesTest {
    @Test
    public void test() {
        final ParsedXML xml = new ParsedXML(xmlFromFileAsString);
        XmlDocument document = xml.document();
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 73);
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).attributes().size(), 1);
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr1");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "value1\">\n  text1\n<root>\n<root attr2\"value2");
    }
}
```

<details>
  <summary><b>Non closing tags with mutiple roots</b></summary><br>

Let's say you have following invalid xml:

```xml
<root1>
  <elm></elm>
</root1>
<root2>
  <elm></elm>
</root2>
<root3>
  <elm1 attr="value" attr="value">
    <elm2 attr="value" attr="value">
      <elm3 attr="value" attr="value">
        <elm4 attr="value" attr="value">
          <elm5 attr="value" attr="value">
            <elm6 attr="value" attr="value">text
```

**Broken XML** in such case assumes that you closed `<root2>` prematurely and will add `<root3>` as child element to `<root2>`.

**Why?** Well, imagine you have just one root that is not closed, do you really want to create another root for unclosed elements? Or let's say you don't have closed elements in your root(which is closed), we don't really want to create a root for non-closed elements which are in our root, right?
So, in another words we will have logical errors in our parser if we do otherwise, and technically it's impossible to detect such tiny things in xml format.

Just remember what you'll get in such exceptional situations. And for God's sake just fix your XMLs.


</details>

## Running checkstyle

```bash
mvn checkstyle:checkstyle
```
