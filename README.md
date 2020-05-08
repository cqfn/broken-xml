# Broken XML

<img src="https://raw.githubusercontent.com/Guseyn/logos/master/broken-xml.svg?sanitize=true">

**Disclaimer:** this project is under active development and not ready yet for usage.

**Broken XML** is a parser that can parse any broken and invalid xml. This parsed should not be used
by any normal human being. But if you'ree lucky like myself, then read further...

## API

**Broken XML** works with a simple string on input:

```java
public class Main {
    static void main(String[] args){ 
        XmlDocument doc = new ParsedXML("<xml>...</xml>").value();
        // You can get list of head elements, if for some reason you have several of them
        List<XmlHeadElement> heads = doc.heads(); 
        // You can get multiple roots
        List<Element> heads = doc.roots();
        // You can get even comments in your xml
        List<Comment> comments = doc.comments();
    }
}
```

## Structure

<details>
  <summary><b>XmlDocument</b></summary><br>
  
  **XmlDocument** is what you get by calling `new ParsedXML(xmlAsString).value()`.
  
  ```java
  // You can get list of head elements, if for some reason you have several of them
  List<XmlHeadElement> heads = doc.heads(); 
  // You can get multiple roots
  List<Element> heads = doc.roots();
  // You can even get comments in your xml
  List<Comment> comments = doc.comments();
  // Also you can get start and end position of your doc
  int start = doc.start(); // is always 0
  int end = doc.end(); // is always a length of xml string
```
</details>


