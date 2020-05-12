import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NestedXmlWithDuplicateAttributesTest extends XmlSource {

    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("nested-xml-with-duplicated-attributes.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 0);
        assertEquals(document.roots().size(), 1);

        assertEquals(document.roots().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
