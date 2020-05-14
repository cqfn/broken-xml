import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MultipleRootsTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("multiple-roots.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 75);
        assertEquals(document.roots().size(), 2);

        assertEquals(document.roots().get(0).name(), "root");
        assertEquals(document.roots().get(0).start(), 0);
        assertEquals(document.roots().get(0).end(), 36);
        assertEquals(document.roots().get(0).attributes().size(), 1);
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr1");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "value1");
        assertEquals(document.roots().get(0).attributes().get(0).nameStart(), 6);
        assertEquals(document.roots().get(0).attributes().get(0).nameEnd(), 10);
        assertEquals(document.roots().get(0).attributes().get(0).valueStart(), 13);
        assertEquals(document.roots().get(0).attributes().get(0).valueEnd(), 18);
        assertEquals(document.roots().get(0).texts().size(), 2);
        assertEquals(document.roots().get(0).texts().get(0).value(), "\n  text1\n");
        assertEquals(document.roots().get(0).texts().get(0).start(), 21);
        assertEquals(document.roots().get(0).texts().get(0).end(), 29);

        assertEquals(document.roots().get(1).name(), "root");
        assertEquals(document.roots().get(1).start(), 38);
        assertEquals(document.roots().get(1).end(), 74);
        assertEquals(document.roots().get(1).attributes().size(), 1);
        assertEquals(document.roots().get(1).attributes().get(0).name(), "attr2");
        assertEquals(document.roots().get(1).attributes().get(0).value(), "value2");
        assertEquals(document.roots().get(1).attributes().get(0).nameStart(), 44);
        assertEquals(document.roots().get(1).attributes().get(0).nameEnd(), 48);
        assertEquals(document.roots().get(1).attributes().get(0).valueStart(), 51);
        assertEquals(document.roots().get(1).attributes().get(0).valueEnd(), 56);
        assertEquals(document.roots().get(1).texts().size(), 1);
        assertEquals(document.roots().get(1).texts().get(0).value(), "\n  text2\n");
        assertEquals(document.roots().get(1).texts().get(0).start(), 59);
        assertEquals(document.roots().get(1).texts().get(0).end(), 67);
    }
}
