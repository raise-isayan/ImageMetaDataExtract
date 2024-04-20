package metadata;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedEditor;
import extension.helpers.HttpResponseWapper;
import extension.view.base.CustomTableModel;
import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author isayan
 */
public class MetaDataTab extends javax.swing.JPanel implements ExtensionProvidedEditor {

    private final static Logger logger = Logger.getLogger(MetaDataTab.class.getName());

    private final EditorCreationContext editorCreationContext;

    /**
     * Creates new form NewJPanel
     *
     * @param editorCreationContext
     */
    public MetaDataTab(EditorCreationContext editorCreationContext) {
        this.editorCreationContext = editorCreationContext;
        initComponents();
        customizeComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableMetaData = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        tableMetaData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Category", "Name", "Type", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableMetaData);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableMetaData;
    // End of variables declaration//GEN-END:variables

    private MetaDataModel modelMetaData = null;
    private HttpRequestResponse httpRequestResponse;

    private final ImageMetaExtract metaData = new ImageMetaExtract();

    private void customizeComponents() {
        this.modelMetaData = new MetaDataModel(this.tableMetaData.getModel());
        this.tableMetaData.setModel(this.modelMetaData);
        this.tableMetaData.setAutoCreateRowSorter(true);

        // Category
        this.tableMetaData.getColumnModel().getColumn(0).setMinWidth(100);
        this.tableMetaData.getColumnModel().getColumn(0).setPreferredWidth(150);
        this.tableMetaData.getColumnModel().getColumn(0).setMaxWidth(300);

        // Name
        this.tableMetaData.getColumnModel().getColumn(1).setMinWidth(100);
        this.tableMetaData.getColumnModel().getColumn(1).setPreferredWidth(200);
        this.tableMetaData.getColumnModel().getColumn(1).setMaxWidth(500);

        // Type
        this.tableMetaData.getColumnModel().getColumn(2).setMinWidth(100);
        this.tableMetaData.getColumnModel().getColumn(2).setPreferredWidth(200);
        this.tableMetaData.getColumnModel().getColumn(2).setMaxWidth(1000);

        // Description
        this.tableMetaData.getColumnModel().getColumn(3).setMinWidth(100);
        this.tableMetaData.getColumnModel().getColumn(3).setPreferredWidth(300);
        this.tableMetaData.getColumnModel().getColumn(3).setMaxWidth(8000);

    }

    public HttpRequestResponse getHttpRequestResponse() {
        return this.httpRequestResponse;
    }

    @Override
    public void setRequestResponse(HttpRequestResponse httpRequestResponse) {
        this.httpRequestResponse = httpRequestResponse;
        HttpResponseWapper wrapResponse = new HttpResponseWapper(this.httpRequestResponse.response());
        if (wrapResponse.hasHttpResponse()) {
            SwingWorker swText = new SwingWorker<List<ImageRowData>, Object>() {
                @Override
                protected List<ImageRowData> doInBackground() throws Exception {
                    ByteArrayInputStream istm = new ByteArrayInputStream(wrapResponse.getBodyByte());
                    ImageMetaData meta = metaData.getMetaData(istm);
                    return meta.getMetaData();
                }

                @Override
                protected void process(List<Object> chunks) {
                }

                @Override
                protected void done() {
                    try {
                        List<ImageRowData> views = get();
                        modelMetaData.removeAll();
                        for (ImageRowData view : views) {
                            modelMetaData.addRow(view);
                        }
                    } catch (InterruptedException | ExecutionException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            };
            swText.execute();
        }

    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse httpRequestResponse) {
        try {
            HttpResponseWapper wrapResponse = new HttpResponseWapper(httpRequestResponse.response());
            if (wrapResponse.hasHttpResponse() && wrapResponse.getBodyByte().length > 0) {
                ImageMetaData meta = this.metaData.getMetaData(new ByteArrayInputStream(wrapResponse.getBodyByte()));
                return meta.hasMetaData();
            }
        } catch (IOException ex) {
            return false;
        }
        return false;
    }

    @Override
    public String caption() {
        return "ImageMeta";
    }

    @Override
    public Component uiComponent() {
        return this;
    }

    @Override
    public Selection selectedData() {
        String selection = CustomTableModel.tableCopy(this.tableMetaData, true);
        return selection.isEmpty() ? null : Selection.selection(ByteArray.byteArray(selection));
    }

    @Override
    public boolean isModified() {
        return false;
    }

}
