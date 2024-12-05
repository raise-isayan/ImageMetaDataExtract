package metadata;

import com.drew.imaging.FileType;
import extension.helpers.HttpMessageWapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

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
        MetaDataITest.class.getResource("/resources/test.jpg"),};

    private final URL[] IMAGE_DOCUMENT_URLS = new URL[]{
        MetaDataITest.class.getResource("/resources/test.pdf"),};

    @Test
    public void testFileType() {
        System.out.println("testFileType");
        String ext = FileType.Png.getName();
        FileType[] types = FileType.values();
        for (FileType t : types) {
            if (t.getName().equals(ext)) {
                System.out.println(t.toString());
            }
        }
    }

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
                    System.out.println("FileType:" + metadata.getFileType());
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
                    System.out.println("FileType:" + metadata.getFileType());
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
                System.out.println("FileType:" + metadata.getFileType());
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

    @Test
    public void testDetectFile() {
        System.out.println("detectFile");
        {
            for (URL u : IMAGE_DOCUMENT_URLS) {
//            for (URL u : IMAGE_URLS) {
                try {
                    System.out.println("url:" + u.toURI());
                    FileInputStream istm = new FileInputStream(Paths.get(u.toURI()).toFile());
                    FileType type = ImageMetaData.detectFileType(istm);
                    System.out.println("type:" + type.getName());
                } catch (URISyntaxException | IOException ex) {
                    System.out.println("exception:" + ex.getClass().getName() + ":" + ex.getMessage());
//                    fail(ex);
                }
            }
        }
    }

    public final static Pattern HTTP_LINESEP = Pattern.compile("\\r\\n\\r\\n");

    @Test
    public void testCrlf() {
        System.out.println("testCrlf");
        String target = "aaaa\r\n\r\nxxxx";
        Pattern p = Pattern.compile("\\r\\n\\r\\n");
        Matcher m = p.matcher(target);
        System.out.println("bfore msg:" + target);
        String msg = m.replaceAll(HttpMessageWapper.LINE_TERMINATE);
        System.out.println("after msg:" + msg);
    }

    @Test
    public void testImageIOMetadata() {
        System.out.println("testImageIOMetadata");
        for (URL u : IMAGE_URLS) {
            try {
                File file = new File(u.toURI());
                System.out.println("image file name:" + file);
                readMetadata(file);
            } catch (URISyntaxException ex) {
                fail(ex);
            }
        }
        for (URL u : IMAGE_DOCUMENT_URLS) {
            try {
                File file = new File(u.toURI());
                System.out.println("document file name:" + file);
                readMetadata(file);
            } catch (URISyntaxException ex) {
                fail(ex);
            }
        }
    }

    private void readMetadata(File file) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(iis, true);
                IIOMetadata metadata = reader.getImageMetadata(0);
                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    System.out.println("Format name: " + names[i]);
                    System.out.println(prettyXml(metadata.getAsTree(names[i]), true));
                }
            }
        } catch (Exception ex) {
            fail(ex);
        }
    }

    public static String prettyXml(Node root, boolean pretty) throws IOException {
        StringWriter sw = new StringWriter();
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, pretty ? "yes" : "no");
            transformer.transform(new DOMSource(root),
                    new StreamResult(sw));
        } catch (TransformerConfigurationException ex) {
            throw new IOException(ex);
        } catch (TransformerException ex) {
            throw new IOException(ex);
        }
        return sw.toString();
    }

}
