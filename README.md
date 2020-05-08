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
        XmlDocument doc = new ParsedXML("<xml>...</xml>").value();
        // You can get list of head elements, if for some reason you have several of them
        List<HeadElement> heads = doc.heads(); 
        // You can get multiple roots
        List<Element> roots = doc.roots();
        // You can get even comments in your xml
        List<Comment> comments = doc.comments();
    }
}
```

### Structure

<details>
  <summary><b>XmlDocument</b></summary><br>
  
  **XmlDocument** is what you get by calling `new ParsedXML(xmlAsString).value()`.
  
  ```java
  XmlDocument doc = new ParsedXML(xmlAsString).value();
  // You can get list of head elements, if for some reason you have several of them
  List<HeadElement> heads = doc.heads();
  // You can get multiple roots
  List<Element> roots = doc.roots();
  // You can even get comments in your xml
  List<Comment> comments = doc.comments();
  // Also you can get start and end position of your doc
  int start = doc.start(); // is always 0
  int end = doc.end(); // is always a length of xml string
```
</details>

<details>
  <summary><b>HeadElement</b></summary><br>
  
  **HeadElement** represents head of xml. It's an element that looks like `<?xml ... ?>`.
  
  ```java
  XmlDocument doc = new ParsedXML(xmlAsString).value();
  HeadElement head = doc.heads().get(0);
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
  XmlDocument doc = new ParsedXML(xmlAsString).value();
  Element element = doc.roots().get(0); // can be aslo retrieved from another element via children() method
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
  XmlDocument doc = new ParsedXML(xmlAsString).value();
  Element element = doc.roots().get(0);
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
  XmlDocument doc = new ParsedXML(xmlAsString).value()
  HeadElement element = doc.heads().get(0)
  Element element = doc.roots().get(0)
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
  XmlDocument doc = new ParsedXML(xmlAsString).value()
  Comment element = doc.comments().get(0)
  // Components:
  String text = comment.text();
  int start = comment.start();
  int end = comment.end();
```
</details>

