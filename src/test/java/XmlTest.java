import java.io.IOException;
import org.apache.commons.io.IOUtils;

public abstract class XmlTest {
    String dataByPath(final String path) throws IOException {
        return IOUtils.toString(
            this.getClass().getResourceAsStream(path),
            "UTF-8"
        ).concat("\n");
    }

    abstract void test() throws IOException;
}
