import com.guseyn.broken_xml.Element;
import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ElementWithMultipleTexts extends XmlTest {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("element-with-multiple-texts.xml"));
        XmlDocument doc = xml.value();
        assertEquals(doc.start(), 0);
        assertEquals(doc.end(), 115);
        assertEquals(doc.roots().size(), 1);

        List<Element> elements = doc.roots().get(0).children();
        assertEquals(elements.size(), 1);
        Element element = elements.get(0);

        assertEquals(element.texts().size(), 3);

        assertEquals(element.texts().get(0).start(), 27);
        assertEquals(element.texts().get(0).end(), 41);
        assertEquals(element.texts().get(0).value(), "\n    text1\n    ");
        assertEquals(element.texts().get(0).value().length() - 1, 41 - 27);

        assertEquals(element.texts().get(1).start(), 57);
        assertEquals(element.texts().get(1).end(), 71);
        assertEquals(element.texts().get(1).value(), "\n    text2\n    ");
        assertEquals(element.texts().get(1).value().length() - 1, 71 - 57);

        assertEquals(element.texts().get(2).start(), 87);
        assertEquals(element.texts().get(2).end(), 99);
        assertEquals(element.texts().get(2).value(), "\n    text3\n  ");
        assertEquals(element.texts().get(2).value().length() - 1, 99 - 87);
    }
}
