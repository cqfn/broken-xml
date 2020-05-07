import com.guseyn.broken_xml.ParsedXML;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ParsedXmlTest {
    @Test
    public void test() throws IOException, NoSuchFieldException, IllegalAccessException {
        final ParsedXML xml = new ParsedXML(dataByPath("valid-xml.xml"));
        xml.value();
    }

    private String dataByPath(final String path) throws IOException {
        return IOUtils.toString(
            this.getClass().getResourceAsStream(path),
            "UTF-8"
        ).concat("\n");
    }
}
