import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ImmediatelyClosingTagsTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("immediately-closing-elements.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).children().size(), 5);
        assertEquals(document.roots().get(0).children().get(0).name(), "infileset");
        assertEquals(document.roots().get(0).children().get(1).name(), "txt");
        assertEquals(document.roots().get(0).children().get(2).name(), "xml");
        assertEquals(document.roots().get(0).children().get(3).name(), "html");
        assertEquals(document.roots().get(0).children().get(4).name(), "js");
        assertEquals(document.roots().get(0).children().get(4).children().get(0).name(), "functions");
        assertEquals(document.roots().get(0).children().get(4).children().get(1).name(), "structures");
        assertEquals(document.roots().get(0).children().get(4).children().get(2).name(), "namespaces");
    }
}
