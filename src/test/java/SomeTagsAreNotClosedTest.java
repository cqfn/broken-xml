import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SomeTagsAreNotClosedTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("some-tags-are-not-closed.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).children().size(), 2);
        assertEquals(document.roots().get(0).children().get(1).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(1).texts().get(0).value(), "text\n");
        assertEquals(document.roots().get(0).children().get(1).texts().get(0).end(), 86);
        assertEquals(document.roots().get(0).children().get(1).end(), 86);
    }
}
