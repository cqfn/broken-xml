import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class NonEscapedBracketsInTexts extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("non-escaped-brackets-in-texts.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).name(), "elm1");
        assertEquals(document.roots().get(0).texts().get(0).value(), "\n  <><<\n  ");
        assertEquals(document.roots().get(0).children().get(0).name(), "elm2");
        assertEquals(document.roots().get(0).children().get(0).texts().get(0).value(), "<><< some text<><< other text");
    }
}
