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
