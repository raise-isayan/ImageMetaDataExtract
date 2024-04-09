package burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;
import burp.api.montoya.ui.editor.extension.HttpResponseEditorProvider;
import extension.burp.BurpExtensionImpl;
import java.util.logging.Logger;
import metadata.MetaDataEditor;

/**
 *
 * @author isayan
 */
public class BurpExtension extends BurpExtensionImpl {

    private final static Logger logger = Logger.getLogger(BurpExtension.class.getName());

    private final HttpResponseEditorProvider responseMetaDataTab = new HttpResponseEditorProvider() {

        @Override
        public ExtensionProvidedHttpResponseEditor provideHttpResponseEditor(EditorCreationContext editorCreationContext) {
            final MetaDataEditor editor = new MetaDataEditor(api(), editorCreationContext);
            return editor;
        }
    };

    @Override
    public void initialize(MontoyaApi api) {
        super.initialize(api);
        api().extension().setName("MetaDataExtract");
        api().userInterface().registerHttpResponseEditorProvider(this.responseMetaDataTab);
    }

}
