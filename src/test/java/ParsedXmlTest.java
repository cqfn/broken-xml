import com.xml.fixer.ParsedXml;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class ParsedXmlTest {

    @Test
    public void test() throws IOException {
        final ParsedXml xml = new ParsedXml(dataByPath("valid-xml.xml"));
        System.out.println(xml.value());
    }

    private String dataByPath(final String path) throws IOException {
        return IOUtils.toString(
            this.getClass().getResourceAsStream(path),
            "UTF-8"
        ).concat("\n");
    }
}
