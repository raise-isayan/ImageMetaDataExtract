package passive.signature;

import extension.burp.scanner.IssueItem;
import java.util.ArrayList;
import java.util.List;
import metadata.ImageRowData;

/**
 *
 * @author isayan
 */
public class ImageMetaDataIssueItem extends IssueItem {

    private String category = new String();

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    private List<ImageRowData> rows = new ArrayList<>();

    /**
     * @return the rows
     */
    public List<ImageRowData> getMetaRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setMetaRows(List<ImageRowData> rows) {
        this.rows = rows;
    }

}
