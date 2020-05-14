import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class OneNonClosedTagTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("one-non-closed-tag.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).name(), "elm");
    }
}
