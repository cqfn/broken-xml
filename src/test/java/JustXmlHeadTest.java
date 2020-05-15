import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class JustXmlHeadTest extends XmlSource {
    @Test
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("single-xml-head.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 1);
        XmlTestUtils.assertXmlHead(document.heads().get(0), "1.0");
        assertEquals(document.heads().get(0).start(), 0);
        assertEquals(document.heads().get(0).end(), 55);
    }
}
