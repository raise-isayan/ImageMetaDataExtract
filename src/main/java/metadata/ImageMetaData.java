package metadata;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.file.FileTypeDirectory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 *
 * @author isayan
 */
public class ImageMetaData {

    private final static Logger logger = Logger.getLogger(ImageMetaData.class.getName());

    private final Metadata metadata;

    public ImageMetaData(Metadata metadata) {
        this.metadata = metadata;
    }

    public List getMetaDirectoryNames() {
        return Arrays.asList(this.metadata.getDirectories());
    }

    public Map<String, List<ImageRowData>> getMetaDataGrop() {
        Map<String, List<ImageRowData>> group = new TreeMap<>();
        for (Directory directory : this.metadata.getDirectories()) {
            List<ImageRowData> views = new ArrayList<>();
            for (Tag tag : directory.getTags()) {
                views.add(new ImageRowData(directory, tag));
            }
            group.put(directory.getName(), views);
        }
        return group;
    }

    public List<ImageRowData> getMetaData() {
        List<ImageRowData> views = new ArrayList<>();
        for (Directory directory : this.metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                views.add(new ImageRowData(directory, tag));
            }
        }
        return views;
    }

    public boolean hasMetaData() {
        List<ImageRowData> views = getMetaData();
        return !views.isEmpty();
    }

    public String getFileTypeName() {
        FileTypeDirectory fileType = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);
        return fileType.getString(FileTypeDirectory.TAG_DETECTED_FILE_TYPE_NAME);
    }

    public String getFileTypeExtension() {
        FileTypeDirectory fileType = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);
        return fileType.getString(FileTypeDirectory.TAG_EXPECTED_FILE_NAME_EXTENSION);
    }

    public String getFileMimeType() {
        FileTypeDirectory fileType = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);
        return fileType.getString(FileTypeDirectory.TAG_DETECTED_FILE_MIME_TYPE);
    }

}
