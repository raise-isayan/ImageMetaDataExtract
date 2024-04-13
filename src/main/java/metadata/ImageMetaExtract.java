package metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ImageMetaExtract {

    private final static Logger logger = Logger.getLogger(ImageMetaExtract.class.getName());

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

    /**
     * @param args the command line arguments
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }

}
