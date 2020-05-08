import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EmptyXml extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("empty.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.heads().size(), 0);
        assertEquals(doc.roots().size(), 0);
        assertEquals(doc.start(), 0);
        assertEquals(doc.end(), 1);
    }
}
