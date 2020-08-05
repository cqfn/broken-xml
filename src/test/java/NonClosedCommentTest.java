import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NonClosedCommentTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("non-closed-comment.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.comments().size(), 1);
        assertEquals(document.comments().get(0).text(), "sfsef\n</elm>");
    }
}
