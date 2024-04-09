package metadata;

import com.drew.metadata.Directory;
import extension.view.base.ObjectTableColumn;
import extension.view.base.ObjectTableMapping;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.drew.metadata.Tag;

/**
 *
 * @author isayan
 */
public class MetaDataView implements ObjectTableMapping {

    private final static Logger logger = Logger.getLogger(MetaDataView.class.getName());

    private final String[] columns = new String[]{
        "Category", "Name", "Type", "Description"
    };

    private final Directory directory;
    private final Tag tag;

    public MetaDataView(Directory directory, Tag tag) {
        this.directory = directory;
        this.tag = tag;
    }

    public ObjectTableColumn getColumn() {
        return new ObjectTableColumn() {
            @Override
            public String getColumnName(int column) {
                return columns[column];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return Object.class;
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }
        };
    }

    private final boolean[] canEdit = new boolean[]{
        false, false, false, false
    };

    @Override
    public boolean isCellEditable(int columnIndex) {
        return this.canEdit[columnIndex];
    }

    @Override
    public Object getObject(int column) {
        Object value = null;
        try {
            switch (column) {
                case 0: // DirectoryName
                {
                    value = this.tag.getDirectoryName();
                    break;
                }
                case 1: // TagName
                {
                    value = this.tag.getTagName();
                    break;
                }
                case 2: // TagType
                {
                    value = this.directory.getString(this.tag.getTagType());
                    break;
                }
                case 3: // Description
                {
                    value = (this.tag.getDescription() == null) ? "" : this.tag.getDescription();
                    break;
                }
                default:
                    break;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return value;
    }

    @Override
    public void setObject(int column, Object value) {
    }

    public Directory getDirectory() {
        return this.directory;
    }

    public Tag getTag() {
        return this.tag;
    }

}
