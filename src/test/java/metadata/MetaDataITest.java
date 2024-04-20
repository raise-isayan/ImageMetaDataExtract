package metadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
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

    private final URL[] IMAGE_URLS = new URL[]{
        MetaDataITest.class.getResource("/resources/test.png"),
        MetaDataITest.class.getResource("/resources/test.jpg"),
        MetaDataITest.class.getResource("/resources/test.bmp"),
        MetaDataITest.class.getResource("/resources/test.gif"),
        MetaDataITest.class.getResource("/resources/test.tiff"),
        MetaDataITest.class.getResource("/resources/test.heic"),};

    private final URL[] IMAGE_IGNORE_URLS = new URL[]{
        MetaDataITest.class.getResource("/resources/test-jpg.png"),
        MetaDataITest.class.getResource("/resources/test-empty.jpg"),
        MetaDataITest.class.getResource("/resources/test-json.jpg"),
        MetaDataITest.class.getResource("/resources/test.jpg"),
    };

    /**
     * Test of getMetaData method, of class MetaData.
     */
    @Test
    public void testGetMetaData() {
        System.out.println("getMetaData");
        {
            ImageMetaExtract imageMeta = new ImageMetaExtract();
            for (URL u : IMAGE_URLS) {
                try {
                    System.out.println("url:" + u.toURI());
                    FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                    ImageMetaData metadata = imageMeta.getMetaData(istm);
                    System.out.println("FileTypeName:" + metadata.getFileTypeName());
                    System.out.println("FileTypeExtension:" + metadata.getFileTypeExtension());
                    System.out.println("FileMimeType:" + metadata.getFileMimeType());
                    for (ImageRowData view : metadata.getMetaData()) {
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

    @Test
    public void testGetIgnoreMetaData() {
        System.out.println("getIgnoreMetaData");
        {
            ImageMetaExtract imageMeta = new ImageMetaExtract();
            for (URL u : IMAGE_IGNORE_URLS) {
                try {
                    System.out.println("url:" + u.toURI());
                    FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                    ImageMetaData metadata = imageMeta.getMetaData(istm);
                    System.out.println("FileTypeName:" + metadata.getFileTypeName());
                    System.out.println("FileTypeExtension:" + metadata.getFileTypeExtension());
                    System.out.println("FileMimeType:" + metadata.getFileMimeType());
                    for (ImageRowData view : metadata.getMetaData()) {
                        System.out.println(view.getTag().getDirectoryName() + ">" + view.getTag().getTagName() + ":" + view.getTag().toString());
                        System.out.println("\tTagTypeHex:" + view.getTag().getTagTypeHex());
                        System.out.println("\tTagName:" + view.getTag().getTagName());
                        System.out.println("\tDescription:" + view.getTag().getDescription());
                    }
                } catch (URISyntaxException | IOException ex) {
                    System.out.println("exception:" + ex.getClass().getName() + ":" + ex.getMessage());
//                    fail(ex);
                }
            }
        }
    }

    /**
     * Test of getMetaData method, of class MetaData.
     */
    @Test
    public void testGetMetaDataGroup() {
        System.out.println("getMetaDataGroup");
        ImageMetaExtract imageMeta = new ImageMetaExtract();
        for (URL u : IMAGE_URLS) {
            try {
                System.out.println("url:" + u.toURI());
                FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                ImageMetaData metadata = imageMeta.getMetaData(istm);
                System.out.println("FileTypeName:" + metadata.getFileTypeName());
                System.out.println("FileTypeExtension:" + metadata.getFileTypeExtension());
                System.out.println("FileMimeType:" + metadata.getFileMimeType());
                Map<String, List<ImageRowData>> metaGroup = metadata.getMetaDataGrop();
                for (String key : metaGroup.keySet()) {
                    List<ImageRowData> views = metaGroup.get(key);
                    for (ImageRowData view : views) {
                        System.out.println(view.getTag().getDirectoryName() + ">" + view.getTag().getTagName() + ":" + view.getTag().toString());
                        System.out.println("\tTagTypeHex:" + view.getTag().getTagTypeHex());
                        System.out.println("\tTagName:" + view.getTag().getTagName());
                        System.out.println("\tDescription:" + view.getTag().getDescription());
                    }
                }
            } catch (URISyntaxException | IOException ex) {
                fail(ex);
            }
        }
    }

}
