import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NoXmlAroundXml extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("no-xml-around-xml.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 1);
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "root");
        assertEquals(document.roots().get(0).texts().get(0).value(), "\n  text\n");
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 126);
    }
}
