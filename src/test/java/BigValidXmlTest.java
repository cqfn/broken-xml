import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BigValidXmlTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("big-valid-xml.xml")
        );
        final String jsonForXml = dataByPath("json-for-big-valid-xml.json");
        XmlDocument document = xml.document();

        assertEquals(jsonForXml, document.json());
    }
}
