import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MultipleXmlHeads extends XmlTest {
    @Test
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("multiple-xml-heads.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.heads().size(), 3);
        XmlTestUtils.assertXmlHead(doc.heads().get(0), "1.0");
        XmlTestUtils.assertXmlHead(doc.heads().get(1), "2.0");
        XmlTestUtils.assertXmlHead(doc.heads().get(2), "3.0");
    }
}
