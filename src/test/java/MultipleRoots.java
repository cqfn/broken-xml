import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MultipleRoots extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("multiple-roots.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.start(), 0);
        assertEquals(doc.end(), 76);
        assertEquals(doc.roots().size(), 2);

        assertEquals(doc.roots().get(0).name(), "root");
        assertEquals(doc.roots().get(0).start(), 0);
        assertEquals(doc.roots().get(0).end(), 36);
        assertEquals(doc.roots().get(0).attributes().size(), 1);
        assertEquals(doc.roots().get(0).attributes().get(0).name(), "attr1");
        assertEquals(doc.roots().get(0).attributes().get(0).value(), "value1");
        assertEquals(doc.roots().get(0).attributes().get(0).nameStart(), 6);
        assertEquals(doc.roots().get(0).attributes().get(0).nameEnd(), 10);
        assertEquals(doc.roots().get(0).attributes().get(0).valueStart(), 13);
        assertEquals(doc.roots().get(0).attributes().get(0).valueEnd(), 18);
        assertEquals(doc.roots().get(0).texts().size(), 2);
        assertEquals(doc.roots().get(0).texts().get(0).value(), "\n  text1\n");
        assertEquals(doc.roots().get(0).texts().get(0).start(), 21);
        assertEquals(doc.roots().get(0).texts().get(0).end(), 29);

        assertEquals(doc.roots().get(1).name(), "root");
        assertEquals(doc.roots().get(1).start(), 38);
        assertEquals(doc.roots().get(1).end(), 74);
        assertEquals(doc.roots().get(1).attributes().size(), 1);
        assertEquals(doc.roots().get(1).attributes().get(0).name(), "attr2");
        assertEquals(doc.roots().get(1).attributes().get(0).value(), "value2");
        assertEquals(doc.roots().get(1).attributes().get(0).nameStart(), 44);
        assertEquals(doc.roots().get(1).attributes().get(0).nameEnd(), 48);
        assertEquals(doc.roots().get(1).attributes().get(0).valueStart(), 51);
        assertEquals(doc.roots().get(1).attributes().get(0).valueEnd(), 56);
        assertEquals(doc.roots().get(1).texts().size(), 1);
        assertEquals(doc.roots().get(1).texts().get(0).value(), "\n  text2\n");
        assertEquals(doc.roots().get(1).texts().get(0).start(), 59);
        assertEquals(doc.roots().get(1).texts().get(0).end(), 67);
    }
}
