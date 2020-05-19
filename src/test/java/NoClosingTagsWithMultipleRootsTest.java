import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NoClosingTagsWithMultipleRootsTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("no-closing-tags-with-multiple-roots.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(1).children().size(), 2);
        assertEquals(document.roots().get(1).name(), "root2");
        assertEquals(document.roots().get(1).children().get(0).name(), "elm");

        assertEquals(document.roots().get(1).children().get(1).name(), "root3");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).name(), "elm1");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).name(), "elm3");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm4");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm5");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 0);
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm6");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(1).children().get(1).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
