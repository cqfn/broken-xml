import com.guseyn.broken_xml.Element;
import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ElementWithMultipleTextsTest extends XmlSource {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("element-with-multiple-texts.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 117);
        assertEquals(document.roots().size(), 1);

        List<Element> elements = document.roots().get(0).children();
        assertEquals(elements.size(), 1);
        Element element = elements.get(0);

        assertEquals(element.texts().size(), 3);

        assertEquals(element.texts().get(0).start(), 27);
        assertEquals(element.texts().get(0).end(), 42);
        assertEquals(element.texts().get(0).value(), ">\n    text1\n    ");
        assertEquals(element.texts().get(0).value().length() - 1, 42 - 27);

        assertEquals(element.texts().get(1).start(), 58);
        assertEquals(element.texts().get(1).end(), 73);
        assertEquals(element.texts().get(1).value(), ">\n    text2\n    ");
        assertEquals(element.texts().get(1).value().length() - 1, 73 - 58);

        assertEquals(element.texts().get(2).start(), 89);
        assertEquals(element.texts().get(2).end(), 102);
        assertEquals(element.texts().get(2).value(), ">\n    text3\n  ");
        assertEquals(element.texts().get(2).value().length() - 1, 102 - 89);
    }
}
