import com.guseyn.broken_xml.ParsedXML;
import com.guseyn.broken_xml.XmlDocument;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DifferentTypeOfOpeningAndClosingQuotesForAttributeValues extends XmlTest  {
    @Test
    @Override
    public void test() throws IOException {
        final ParsedXML xml = new ParsedXML(dataByPath("different-type-of-opening-and-closing-quotes-for-attribute-values.xml"));
        XmlDocument document = xml.document();
        assertEquals(document.start(), 0);
        assertEquals(document.end(), 74);
        assertEquals(document.roots().size(), 2);
        assertEquals(document.roots().get(0).attributes().size(), 1);
        assertEquals(document.roots().get(0).attributes().get(0).name(), "attr1");
        assertEquals(document.roots().get(0).attributes().get(0).value(), "value1");
        assertEquals(document.roots().get(1).attributes().size(), 1);
        assertEquals(document.roots().get(1).attributes().get(0).name(), "attr2");
        assertEquals(document.roots().get(1).attributes().get(0).value(), "value2");
    }
}
