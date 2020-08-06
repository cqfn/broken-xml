import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class HadoopPomTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("hadoop-pom.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.heads().size(), 1);
        assertEquals(document.roots().size(), 1);
    }
}
