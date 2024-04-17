package passive.signature;

import extension.burp.Severity;
import extension.burp.scanner.SignatureItem;
import extension.burp.scanner.SignatureScanBase;

/**
 *
 * @author isayan
 */
public class ImageMetaDataSignature extends SignatureItem {

    public ImageMetaDataSignature(SignatureScanBase<? extends ImageMetaDataIssueItem> item, Severity serverity) {
        super(item, serverity);
    }

    public ImageMetaDataSignature() {
        this(new ImageMetaDataScan(), Severity.LOW);
    }


}
