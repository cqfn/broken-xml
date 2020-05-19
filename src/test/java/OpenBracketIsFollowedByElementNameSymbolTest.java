import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class OpenBracketIsFollowedByElementNameSymbolTest extends XmlSource {
    @Test
    @Override
    void test() throws IOException {
        final ParsedXML xml = new ParsedXML(
            dataByPath("open-bracket-is-followed-by-element-name-symbol.xml")
        );
        XmlDocument document = xml.document();
        assertEquals(document.roots().get(0).name(), "elm");
        assertEquals(document.roots().get(0).children().get(0).name(), "sometext");
    }
}
