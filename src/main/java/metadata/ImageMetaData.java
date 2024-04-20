package metadata;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.file.FileTypeDirectory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public FileType getFileType() {
        FileType fileType = FileType.Unknown;
        String fileTypeName = getFileTypeName();
        FileType[] types = FileType.values();
        for (FileType t : types) {
            if (t.getName().equals(fileTypeName)) {
                fileType = t;
                break;
            }
        }
        return fileType;
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

    public static FileType detectFileType(InputStream istm) throws IOException {
        if (istm.markSupported()) {
            return FileTypeDetector.detectFileType(istm);
        } else {
            return FileTypeDetector.detectFileType(new BufferedInputStream(istm));
        }
    }

    public static boolean supportFileType(FileType fileType) {
        switch (fileType) {
            case Jpeg:
            case Tiff:
            case Arw:
            case Cr2:
            case Nef:
            case Orf:
            case Rw2:
            case Psd:
            case Png:
            case Bmp:
            case Gif:
            case Ico:
            case Pcx:
            case WebP:
            case Raf:
            case Avi:
            case Wav:
            case QuickTime:
            case Mp4:
            case Mp3:
            case Eps:
            case Heif:
                return true;
        }
        return false;
    }

}
