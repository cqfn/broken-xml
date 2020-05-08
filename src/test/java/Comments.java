import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Comments extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("comments.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.comments().size(), 5);
        assertEquals(doc.comments().get(0).text(), "");
        assertEquals(doc.comments().get(0).start(), 0);
        assertEquals(doc.comments().get(0).end(), 6);

        assertEquals(doc.comments().get(1).text(), " ");
        assertEquals(doc.comments().get(1).start(), 8);
        assertEquals(doc.comments().get(1).end(), 15);

        assertEquals(doc.comments().get(2).text(), "some text");
        assertEquals(doc.comments().get(2).start(), 17);
        assertEquals(doc.comments().get(2).end(), 32);

        assertEquals(doc.comments().get(3).text(), "sometext");
        assertEquals(doc.comments().get(3).start(), 34);
        assertEquals(doc.comments().get(3).end(), 48);

        assertEquals(doc.comments().get(4).text(), "\\n");
        assertEquals(doc.comments().get(4).start(), 50);
        assertEquals(doc.comments().get(4).end(), 58);
    }
}
