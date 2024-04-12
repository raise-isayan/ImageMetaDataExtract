package metadata;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isayan
 */
public class ImageMetaData {

    private final Metadata metadata;

    public ImageMetaData(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<ImageRowData> getMetaData() {
        List<ImageRowData> vies = new ArrayList<>();
        for (Directory directory : this.metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                vies.add(new ImageRowData(directory, tag));
            }
        }
        return vies;
    }

    public boolean hasMetaData() {
        List<ImageRowData> views = getMetaData();
        return !views.isEmpty();
    }

}
