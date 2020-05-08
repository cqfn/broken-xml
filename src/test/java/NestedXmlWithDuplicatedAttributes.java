import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NestedXmlWithDuplicatedAttributes extends XmlTest  {

    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("nested-xml-with-duplicated-attributes.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.heads().size(), 0);
        assertEquals(doc.roots().size(), 1);

        assertEquals(doc.roots().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(doc.roots().get(0).children().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
