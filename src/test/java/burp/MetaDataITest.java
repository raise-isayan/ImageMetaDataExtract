package burp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import metadata.ImageMetaData;
import metadata.ImageMetaExtract;
import metadata.ImageRowData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author isayan
 */
public class MetaDataITest {

    public MetaDataITest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    private URL[] urls = new URL[]{
        MetaDataITest.class.getResource("/resources/test.png"),
        MetaDataITest.class.getResource("/resources/test.jpg"),
        MetaDataITest.class.getResource("/resources/test.bmp"),
        MetaDataITest.class.getResource("/resources/test.gif"),
        MetaDataITest.class.getResource("/resources/test.heic"),};

    /**
     * Test of getMetaData method, of class MetaData.
     */
    @Test
    public void testGetMetaData() {
        System.out.println("getMetaData");
        {
            for (URL u : urls) {
                try {
                    System.out.println("url:" + u.toURI());
                    FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                    ImageMetaExtract metadata = new ImageMetaExtract();
                    ImageMetaData meta = metadata.getMetaData(istm);
                    for (ImageRowData view : meta.getMetaData()) {
                        System.out.println(view.getTag().getDirectoryName() + ">" + view.getTag().getTagName() + ":" + view.getTag().toString());
                        System.out.println("\tTagTypeHex:" + view.getTag().getTagTypeHex());
                        System.out.println("\tTagName:" + view.getTag().getTagName());
                        System.out.println("\tDescription:" + view.getTag().getDescription());
                    }
                } catch (URISyntaxException | IOException ex) {
                    fail(ex);
                }
            }
        }
    }

}
