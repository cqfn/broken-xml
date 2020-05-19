import com.guseyn.broken_xml.Attribute;
import com.guseyn.broken_xml.HeadElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlTestUtils {

    static void assertXmlHead(HeadElement head, String version) {
        List<Attribute> attributes = head.attributes();
        assertEquals(attributes.size(), 3);
        Attribute firstAttribute = attributes.get(0);
        Attribute secondAttribute = attributes.get(1);
        Attribute thirdAttribute = attributes.get(2);
        assertEquals(firstAttribute.name(), "version");
        assertEquals(firstAttribute.value(), version);
        assertEquals(secondAttribute.name(), "encoding");
        assertEquals(secondAttribute.value(), "UTF-8");
        assertEquals(thirdAttribute.name(), "attribute");
        assertEquals(thirdAttribute.value(), "value");
    }

}
