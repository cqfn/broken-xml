import com.guseyn.broken_xml.Attribute;
import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import com.guseyn.broken_xml.XmlHeadElement;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ParsedXmlTest {
    @Test
    public void justXmlHead() throws IOException, NoSuchFieldException, IllegalAccessException {
        final ParsedXML xml = new ParsedXML(dataByPath("just-xml-head.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.heads().size(), 1);
        assertXmlHead(doc.heads().get(0), "1.0");
    }

    @Test
    public void multipleXmlHeads() throws IOException, NoSuchFieldException, IllegalAccessException {
        final ParsedXML xml = new ParsedXML(dataByPath("multiple-xml-heads.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.heads().size(), 3);
        assertXmlHead(doc.heads().get(0), "1.0");
        assertXmlHead(doc.heads().get(1), "2.0");
        assertXmlHead(doc.heads().get(2), "3.0");
    }

    private String dataByPath(final String path) throws IOException {
        return IOUtils.toString(
            this.getClass().getResourceAsStream(path),
            "UTF-8"
        ).concat("\n");
    }

    private void assertXmlHead(XmlHeadElement head, String version) {
        List<Attribute> attributes = head.attributes();
        assertEquals(attributes.size(), 3);
        Attribute firstAttribute = attributes.get(0);
        Attribute secondAttribute = attributes.get(1);
        Attribute thirdAttribute = attributes.get(2);
        assertEquals(firstAttribute.name(), "version");
        assertEquals(firstAttribute.value(), version);
        assertEquals(secondAttribute.name(), "encoding");
        assertEquals(secondAttribute.value(), "UTF-8");
        assertEquals(thirdAttribute.name(), "attribute");
        assertEquals(thirdAttribute.value(), "value");
    }
}
