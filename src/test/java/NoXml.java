import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NoXml extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("no-xml.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 0);
        assertEquals(document.roots().size(), 0);
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 25);
    }
}
