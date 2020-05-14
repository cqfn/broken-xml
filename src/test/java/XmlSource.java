import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public abstract class XmlSource {
    String dataByPath(final String path) throws IOException {
        return FileUtils.readFileToString(
            new File("src/test/resources/".concat(path)), "UTF-8"
        );
    }

    abstract void test() throws IOException;
}
