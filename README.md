# Broken XML

<img src="https://raw.githubusercontent.com/Guseyn/logos/master/broken-xml.svg?sanitize=true">

**Disclaimer:** this project is under active development and not ready yet for usage.

**Broken XML** is a parser that can parse any broken and invalid xml. This parser should not be used
by any normal human being. But if you're lucky like myself, just read further...

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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
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
  XmlDocument doc = new ParsedXML(xmlAsString).document();
  Comment comment = document.comments().get(0);
  // Components:
  String text = comment.text();
  int start = comment.start();
  int end = comment.end();
```
</details>

## How broken is your XML?

### Empty xml

If you have an empty xml, no problem, you'll get just empty `XmlDocument`:

```java
public class EmptyXml {
    @Test
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML("");
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 0);
        assertEquals(document.roots().size(), 0);
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 0);
    }
}
```

### XML that is wrapped with some other text

**Broken XML** allows you to have xml text with no xml stuff, in such case it will return information only about xml part:

```java
public class NoXmlAroundXml {
    @Test
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML("Some text here<root attr="value">text</root>and some text here");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "root");
        assertEquals(document.roots().get(0).texts().get(0).value(), "text");
    }
}
```

### Multiple roots

Valid xml contains only one root element. But **Broken XML** does not care and returns multiple roots as a list:

```java
public class MultipleRoots {
    @Test
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML("<root1></root1><root2></root2>");
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 2);
        assertEquals(document.roots().get(0).name(), "root1");
        assertEquals(document.roots().get(1).name(), "root2");
    }
}
```

### Duplicate attributes in elements

It does not matter anymore if elements in your xml have duplicate attribute names, **Broken XML** will return a list of them:

```java
public class DuplicateAttributesInElement {
    @Test
    public void test() throws IOException {
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

...to be continued
