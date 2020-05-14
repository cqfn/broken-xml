import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NonEscapedQuotesTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("non-escaped-quotes.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().size(), 1);
        assertEquals(document.roots().get(0).name(), "elm");
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "\"va\"\"lu\"\"e");
    }
}
