# xml-fixer
Fix invalid xml as much as it's possible. 

 - [ ] escape special charaters
 - [ ] split document with multiple roots into several documents

# XML escaped characters

-	`&amp;`	&
-	`&lt;`	<
-	`&gt;`	>
-	`&quot;`	"
-	`&apos;`	'

# Well formed XML documents

Well formed XML documents

The basic rules for creating well-formed xml.

- Your XML file must have a root element.
- XML elements must have a closing tag.
- All opening and closing tags must match.
- You must enclose all attribute values in single or double quotation marks.
- All tags must be nested correctly.
- All entities must be declared.

# When it does make sense to fix xml, cases

Knowing that XML elements must follow these naming rules:

- Element names are case-sensitive
- Element names must start with a letter or underscore
- Element names cannot start with the letters xml (or XML, or Xml, etc)
- Element names can contain letters, digits, hyphens, underscores, and periods
- Element names cannot contain spaces

We can make rules for fixing xml for following cases.

## Escape brackets < and > inside of elements when they are part of words that are surrounded by spaces or breaklines.

```xml
<!-- invalid -->
<elm><fsdfsdf </elm>
<!-- valid -->
<elm>&lt;<fsdfsdf </elm>
  
<!-- invalid -->
<elm>< fsdfsdf ></elm>
<!-- valid -->
<elm>&lt; fsdfsdf &gt;</elm>
```

## Escape brackets < and > inside of elements when they are part of words with no charaters.

```xml
<!-- invalid -->
<elm><<<<>>>></elm>
<!-- valid -->
<elm>>&lt;&lt;&lt;&lt;&gt;&gt;&gt;&gt;</elm>
```

## Escape brackets < and > inside of elements when > goes after > or < goes after <.

 It's bit dfferent from the case above because here we cannot have sequence `< >`.

```xml
<!-- invalid -->
<elm><a<s<d<f</elm>
<!-- valid -->
<elm>>&lt;a&lt;s&lt;d&lt;f</elm>

<!-- invalid -->
<elm>f>d>s>a></elm>
<!-- valid -->
<elm>f&gt;d&gt;s&gt;a&gt;</elm>
```

## How to parse XML

Very useful stuff: https://gist.github.com/zeux/5346409

## Example of valid XML

```
<?xml version="1.0" encoding="UTF-8"?>
<breakfast_menu>
  <food>
    <name>Belgian Waffles</name>
    <price>$5.95</price>
    <description>Two of our famous Belgian Waffles with plenty of real maple syrup</description>
    <calories>650</calories>
  </food>
  <food>
    <name>Strawberry Belgian Waffles</name>
    <price>$7.95</price>
    <description>Light Belgian waffles covered with strawberries and whipped cream</description>
    <calories>900</calories>
  </food>
  <food>
    <name>Berry-Berry Belgian Waffles</name>
    <price>$8.95</price>
    <description>Light Belgian waffles covered with an assortment of fresh berries and whipped cream</description>
    <calories>900</calories>
  </food>
  <food>
    <name>French Toast</name>
    <price>$4.50</price>
    <description>Thick slices made from our homemade sourdough bread</description>
    <calories>600</calories>
  </food>
  <food>
    <name>Homestyle Breakfast</name>
    <price>$6.95</price>
    <description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description>
    <calories>950</calories>
  </food>
</breakfast_menu>
<?xml version="1.0" encoding="UTF-8"?>
<breakfast_menu>
  <food>
    <name>Belgian Waffles</name>
    <price>$5.95</price>
    <description>Two of our famous Belgian Waffles with plenty of real maple syrup</description>
    <calories>650</calories>
  </food>
  <food>
    <name>Strawberry Belgian Waffles</name>
    <price>$7.95</price>
    <description>Light Belgian waffles covered with strawberries and whipped cream</description>
    <calories>900</calories>
  </food>
  <food>
    <name>Berry-Berry Belgian Waffles</name>
    <price>$8.95</price>
    <description>Light Belgian waffles covered with an assortment of fresh berries and whipped cream</description>
    <calories>900</calories>
  </food>
  <food>
    <name>French Toast</name>
    <price>$4.50</price>
    <description>Thick slices made from our homemade sourdough bread</description>
    <calories>600</calories>
  </food>
  <food>
    <name>Homestyle Breakfast</name>
    <price>$6.95</price>
    <description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description>
    <calories>950</calories>
  </food>
</breakfast_menu>
```
