package burp;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import metadata.MetaDataView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
        MetaDataITest.class.getResource("/resources/test.png"),
        MetaDataITest.class.getResource("/resources/test.bmp"),
        MetaDataITest.class.getResource("/resources/test.gif"),};

    /**
     * Test of getMetaData method, of class MetaData.
     */
    @Test
    public void testGetMetaData() throws Exception {
        System.out.println("getMetaData");
        {
            for (URL u : urls) {
                System.out.println("url:" + u.toURI());
                FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                MetaData metadata = new MetaData();
                List<MetaDataView> maps = metadata.getMetaData(istm);
                for (MetaDataView map : maps) {
                    System.out.println(map.getTag().getTagName());
                }
            }
        }
    }

}
