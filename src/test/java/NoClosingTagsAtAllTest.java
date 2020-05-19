import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NoClosingTagsAtAllTest extends XmlSource  {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("no-closing-tags-at-all.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).name(), "elm1");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).name(), "elm3");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm4");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm5");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");

        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().size(), 1);
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).name(), "elm6");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).attributes().get(0).value(), "value");
        assertEquals(document.roots().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).children().get(0).texts().get(0).value(), "text");
    }
}
