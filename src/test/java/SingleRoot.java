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
        XmlDocument doc = xml.value();
        assertEquals(doc.start(), 0);
        assertEquals(doc.end(), 35);
        assertEquals(doc.roots().size(), 1);
        assertEquals(doc.roots().get(0).name(), "root");
        assertEquals(doc.roots().get(0).start(), 0);
        assertEquals(doc.roots().get(0).end(), 33);
        assertEquals(doc.roots().get(0).attributes().size(), 1);
        assertEquals(doc.roots().get(0).attributes().get(0).name(), "attr");
        assertEquals(doc.roots().get(0).attributes().get(0).value(), "value");
        assertEquals(doc.roots().get(0).attributes().get(0).nameStart(), 6);
        assertEquals(doc.roots().get(0).attributes().get(0).nameEnd(), 9);
        assertEquals(doc.roots().get(0).attributes().get(0).valueStart(), 12);
        assertEquals(doc.roots().get(0).attributes().get(0).valueEnd(), 16);
        assertEquals(doc.roots().get(0).texts().size(), 1);
        assertEquals(doc.roots().get(0).texts().get(0).value(), "\n  text\n");
        assertEquals(doc.roots().get(0).texts().get(0).start(), 19);
        assertEquals(doc.roots().get(0).texts().get(0).end(), 26);
    }
}
