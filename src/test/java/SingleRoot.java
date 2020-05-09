import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SingleRoot extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("single-root.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 35);
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "root");
        assertEquals(document.roots().get(0).start(), 0);
        assertEquals(document.roots().get(0).end(), 33);
        assertEquals(document.roots().get(0).attributes().size(), 1);
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "document");
        assertEquals(document.roots().get(0).attributes().get(0).nameStart(), 6);
        assertEquals(document.roots().get(0).attributes().get(0).nameEnd(), 9);
        assertEquals(document.roots().get(0).attributes().get(0).valueStart(), 12);
        assertEquals(document.roots().get(0).attributes().get(0).valueEnd(), 16);
        assertEquals(document.roots().get(0).texts().size(), 1);
        assertEquals(document.roots().get(0).texts().get(0).value(), "\n  text\n");
        assertEquals(document.roots().get(0).texts().get(0).start(), 19);
        assertEquals(document.roots().get(0).texts().get(0).end(), 26);
    }
}
