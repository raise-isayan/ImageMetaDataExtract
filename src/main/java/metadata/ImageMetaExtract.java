package metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageMetaExtract {

    private final static Logger logger = Logger.getLogger(ImageMetaExtract.class.getName());

    private final static java.util.ResourceBundle RELEASE = java.util.ResourceBundle.getBundle("burp/resources/release");

    public ImageMetaExtract() {
    }

    public ImageMetaData getMetaData(InputStream istm) throws IOException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(istm);
            ImageMetaData meta = new ImageMetaData(metadata);
            return meta;
        } catch (ImageProcessingException ex) {
            throw new IOException(ex);
        }
    }

    private static String getVersion() {
        return RELEASE.getString("version");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String targetFileName = null;
        try {
            for (String arg : args) {
                // single parameter
                if ("-v".equals(arg)) {
                    System.out.println("Version: " + getVersion());
                    System.out.println("Language: " + Locale.getDefault().getLanguage());
                    System.exit(0);
                }
                if ("-h".equals(arg)) {
                    usage();
                    System.exit(0);
                }
                String[] param = arg.split("=", 2);
                if (param.length < 2) {
                    // single parameter
                } else {
                    // multi parameter
                    if ("-file".equals(param[0])) {
                        targetFileName = param[1];
                    }
                }
            }

            // 必須チェック
            if (targetFileName == null) {
                System.out.println("-file argument err ");
                usage();
                return;
            } else {
                File file = new File(targetFileName);
                if (!file.exists()) {
                    System.out.println("file not found:" + file.getAbsolutePath());
                    usage();
                    return;
                }
                ImageMetaExtract imageMeta = new ImageMetaExtract();
                ImageMetaData meta = imageMeta.getMetaData(new FileInputStream(file));
                System.out.println("Category\tTagName\tTagType\tDescription");
                for (ImageRowData view : meta.getMetaData()) {
                    System.out.print(view.getTag().getDirectoryName());
                    System.out.print("\t");
                    System.out.print(view.getTag().getTagName());
                    System.out.print("\t");
                    System.out.print(view.getTag().getTagTypeHex());
                    System.out.print("\t");
                    System.out.print(view.getTag().getDescription());
                    System.out.println();
                }
                return;
            }

        } catch (Exception ex) {
            String errmsg = String.format("%s: %s", ex.getClass().getName(), ex.getMessage());
            System.out.println(errmsg);
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            usage();
        }
    }

    private static void usage() {
        System.out.println("");
        System.out.println(String.format("Usage: java -jar %s.jar [option] -file=ImagePath", ImageMetaExtract.class.getSimpleName()));
        System.out.println("[option]");
        System.out.println("\t-h: output help.");
        System.out.println("\t-v: output version.");
        System.out.println("[command]");
        System.out.println("\t-file=<Image File>: Image File Path");
        System.out.println("");
    }

}
