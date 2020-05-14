import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommentsTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("comments.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.comments().size(), 5);
        assertEquals(document.comments().get(0).text(), "");
        assertEquals(document.comments().get(0).start(), 0);
        assertEquals(document.comments().get(0).end(), 6);

        assertEquals(document.comments().get(1).text(), " ");
        assertEquals(document.comments().get(1).start(), 8);
        assertEquals(document.comments().get(1).end(), 15);

        assertEquals(document.comments().get(2).text(), "some><>< text");
        assertEquals(document.comments().get(2).start(), 17);
        assertEquals(document.comments().get(2).end(), 36);

        assertEquals(document.comments().get(3).text(), "sometext");
        assertEquals(document.comments().get(3).start(), 38);
        assertEquals(document.comments().get(3).end(), 52);

        assertEquals(document.comments().get(4).text(), "\\n");
        assertEquals(document.comments().get(4).start(), 54);
        assertEquals(document.comments().get(4).end(), 62);
    }
}
