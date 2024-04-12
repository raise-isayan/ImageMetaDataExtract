package metadata;

import extension.view.base.DefaultObjectTableModel;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author isayan
 */
public class MetaDataModel extends DefaultObjectTableModel<ImageRowData> {

    private final static Logger logger = Logger.getLogger(MetaDataModel.class.getName());

    public MetaDataModel(TableModel table) {
        super(table);
    }

}
