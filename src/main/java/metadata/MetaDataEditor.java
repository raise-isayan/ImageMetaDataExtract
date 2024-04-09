package metadata;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;
import java.awt.Component;
import java.util.logging.Logger;

/**
 *
 * @author isayan
 */
public class MetaDataEditor implements ExtensionProvidedHttpResponseEditor {

    private final static Logger logger = Logger.getLogger(MetaDataEditor.class.getName());

    private final MontoyaApi api;
    private final MetaDataTab tabMetaData;

    public MetaDataEditor(MontoyaApi api, EditorCreationContext editorCreationContext) {
        this.api = api;
        this.tabMetaData = new MetaDataTab(editorCreationContext);
    }

    @Override
    public HttpResponse getResponse() {
        HttpRequestResponse http = this.tabMetaData.getHttpRequestResponse();
        return http.response();
    }

    @Override
    public void setRequestResponse(HttpRequestResponse httpRequestResponse) {
        this.tabMetaData.setRequestResponse(httpRequestResponse);
    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse httpRequestResponse) {
        return this.tabMetaData.isEnabledFor(httpRequestResponse);
    }

    @Override
    public String caption() {
        return this.tabMetaData.caption();
    }

    @Override
    public Component uiComponent() {
        return this.tabMetaData.uiComponent();
    }

    @Override
    public Selection selectedData() {
        return this.tabMetaData.selectedData();
    }

    @Override
    public boolean isModified() {
        return this.tabMetaData.isModified();
    }

}
