package burp;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import metadata.MetaDataView;

public class MetaData {

    private final static Logger logger = Logger.getLogger(MetaData.class.getName());

    public MetaData() {

    }

    public List<MetaDataView> getMetaData(InputStream istm) throws IOException {
        try {
            List<MetaDataView> tags = new ArrayList<>();
            Metadata metadata = ImageMetadataReader.readMetadata(istm);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    tags.add(new MetaDataView(directory, tag));
                }
            }
            return tags;
        } catch (ImageProcessingException ex) {
            throw new IOException(ex);
        }
    }

}
