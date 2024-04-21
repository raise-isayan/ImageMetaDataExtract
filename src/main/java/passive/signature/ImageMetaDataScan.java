package passive.signature;

import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;
import burp.api.montoya.scanner.audit.issues.AuditIssueDefinition;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;
import com.drew.imaging.FileType;
import extension.burp.Confidence;
import extension.burp.Severity;
import extension.burp.scanner.IssueItem;
import extension.burp.scanner.ScannerCheckAdapter;
import extension.burp.scanner.SignatureScanBase;
import extension.helpers.HttpResponseWapper;
import extension.helpers.HttpUtil;
import extension.helpers.MatchUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.ImageMetaData;
import metadata.ImageMetaExtract;
import metadata.ImageRowData;

/**
 *
 * @author isayan
 */
public class ImageMetaDataScan extends SignatureScanBase<ImageMetaDataIssueItem> {

    private final static Logger logger = Logger.getLogger(ImageMetaDataScan.class.getName());

    public ImageMetaDataScan() {
        super("Image MetaData Discover");
    }

    @Override
    public ScanCheck passiveScanCheck() {
        final ImageMetaExtract meta = new ImageMetaExtract();
        return new ScannerCheckAdapter() {
            @Override
            public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
                List<AuditIssue> issues = new ArrayList<>();
                try {
                    // Response判定
                    HttpResponseWapper wrapResponse = new HttpResponseWapper(baseRequestResponse.response());
                    if (wrapResponse.hasHttpResponse() && wrapResponse.getBodyByte().length > 0) {
//                        FileType fileType = ImageMetaExtract.detectFileType(new ByteArrayInputStream(wrapResponse.getBodyByte()));
//                        if (!ImageMetaExtract.supportFileType(fileType)) {
//                            return AuditResult.auditResult(issues);
//                        }
                        ImageMetaData imageMeta = meta.getMetaData(new ByteArrayInputStream(wrapResponse.getBodyByte()));
                        if (imageMeta.hasMetaData()) {
                            Map<String, List<ImageRowData>> metaGroup = imageMeta.getMetaDataGrop();
                            for (String key : metaGroup.keySet()) {
                                List<ImageRowData> rows = metaGroup.get(key);
                                /* Content-Type Mismatch */
                                if (wrapResponse.getContentMimeType() != null && !imageMeta.getFileMimeType().equals(wrapResponse.getContentMimeType())) {
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    final StringBuilder detail = new StringBuilder();
                                    detail.append("<h4>Content-Type mismatch of image:</h4>");
                                    detail.append("<div>");
                                    detail.append("<p>Response Content-Type Header:</p>");
                                    detail.append("<p>");
                                    detail.append("<code>");
                                    detail.append(wrapResponse.getContentMimeType());
                                    detail.append("</code>");
                                    detail.append("</p>");
                                    detail.append("<p>Image of MimeType:</p>");
                                    detail.append("<p>");
                                    detail.append("<code>");
                                    detail.append(imageMeta.getFileMimeType());
                                    detail.append("</code>");
                                    detail.append("</p>");

                                    final String ISSUE_BACKGROUND = "\r\n"
                                        + "<h4>Content-Type mismatch of image:</h4>"
                                        + "<ul>"
                                        + "Response Content-Type and image format mismatch"
                                        + "</ul>";

                                    AuditIssue issueItem = AuditIssue.auditIssue(
                                        getIssueName(),
                                        detail.toString(),
                                        null,
                                        baseRequestResponse.request().url(),
                                        AuditIssueSeverity.INFORMATION, AuditIssueConfidence.CERTAIN,
                                        ISSUE_BACKGROUND,
                                        null,
                                        AuditIssueSeverity.INFORMATION,
                                        baseRequestResponse);
                                    issues.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                }
                                /* JPEG */
                                if (((FileType.Jpeg.getName().equals(imageMeta.getFileTypeName()) && key.startsWith("JpegComment")))) {
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    // メールアドレス含む場合はリスクをあげる
                                    boolean containMail = rows.stream().anyMatch(row -> MatchUtil.containsMailAddress(row.getTag().getDescription()));
                                    ImageMetaDataIssueItem issueItem = new ImageMetaDataIssueItem();
                                    issueItem.setType(imageMeta.getFileTypeName() + " " + "Comment");
                                    issueItem.setMessageIsRequest(false);
                                    issueItem.setServerity(Severity.INFORMATION);
                                    if (containMail) {
                                        issueItem.setServerity(Severity.LOW);
                                    }
                                    issueItem.setConfidence(Confidence.CERTAIN);
                                    issueItem.setCaptureValue("");
                                    issueItem.setCategory(key);
                                    issueItem.setMetaRows(rows);
                                    issueList.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                }
                                if (((FileType.Jpeg.getName().equals(imageMeta.getFileTypeName()))
                                        || (FileType.Tiff.getName().equals(imageMeta.getFileTypeName()))
                                        || (FileType.Heif.getName().equals(imageMeta.getFileTypeName())))
                                        && key.startsWith("Exif ")) {
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    // メールアドレス含む場合はリスクをあげる
                                    boolean containMail = rows.stream().anyMatch(row -> MatchUtil.containsMailAddress(row.getTag().getDescription()));
                                    ImageMetaDataIssueItem issueItem = new ImageMetaDataIssueItem();
                                    issueItem.setType(imageMeta.getFileTypeName() + " " + "Exif");
                                    issueItem.setMessageIsRequest(false);
                                    issueItem.setServerity(Severity.INFORMATION);
                                    if (containMail) {
                                        issueItem.setServerity(Severity.LOW);
                                    }
                                    issueItem.setConfidence(Confidence.CERTAIN);
                                    issueItem.setCaptureValue("");
                                    issueItem.setCategory(key);
                                    issueItem.setMetaRows(rows);
                                    issueList.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                } else if (((FileType.Jpeg.getName().equals(imageMeta.getFileTypeName()))
                                        || (FileType.Tiff.getName().equals(imageMeta.getFileTypeName()))
                                        || (FileType.Heif.getName().equals(imageMeta.getFileTypeName())))
                                        && key.startsWith("GPS")) {
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    ImageMetaDataIssueItem issueItem = new ImageMetaDataIssueItem();
                                    issueItem.setType(imageMeta.getFileTypeName() + " " + "GPS");
                                    issueItem.setMessageIsRequest(false);
                                    issueItem.setServerity(Severity.LOW);
                                    issueItem.setConfidence(Confidence.CERTAIN);
                                    issueItem.setCaptureValue("");
                                    issueItem.setCategory(key);
                                    issueItem.setMetaRows(rows);
                                    issueList.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                }
                                /* PNG */
                                if (FileType.Png.getName().equals(imageMeta.getFileTypeName())
                                        && key.startsWith("PNG-tEXt")) {
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    // メールアドレス含む場合はリスクをあげる
                                    boolean containMail = rows.stream().anyMatch(row -> MatchUtil.containsMailAddress(row.getTag().getDescription()));
                                    ImageMetaDataIssueItem issueItem = new ImageMetaDataIssueItem();
                                    issueItem.setType(imageMeta.getFileTypeName() + " " + "tExt");
                                    issueItem.setMessageIsRequest(false);
                                    issueItem.setServerity(Severity.INFORMATION);
                                    if (containMail) {
                                        issueItem.setServerity(Severity.LOW);
                                    }
                                    issueItem.setConfidence(Confidence.CERTAIN);
                                    issueItem.setCaptureValue("");
                                    issueItem.setCategory(key);
                                    issueItem.setMetaRows(rows);
                                    issueList.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                }
                                /* GIF */
                                if ((FileType.Gif.getName().equals(imageMeta.getFileTypeName()) && key.startsWith("GIF Comment"))) {
                                    boolean containMail = rows.stream().anyMatch(row -> MatchUtil.containsMailAddress(row.getTag().getDescription()));
                                    List<ImageMetaDataIssueItem> issueList = new ArrayList<>();
                                    ImageMetaDataIssueItem issueItem = new ImageMetaDataIssueItem();
                                    issueItem.setType(imageMeta.getFileTypeName() + " " + "Comment");
                                    issueItem.setMessageIsRequest(false);
                                    issueItem.setServerity(Severity.INFORMATION);
                                    if (containMail) {
                                        issueItem.setServerity(Severity.LOW);
                                    }
                                    issueItem.setConfidence(Confidence.CERTAIN);
                                    issueItem.setCaptureValue("");
                                    issueItem.setMetaRows(rows);
                                    issueList.add(issueItem);
                                    issues.add(makeScanIssue(baseRequestResponse, issueList));
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
                return AuditResult.auditResult(issues);
            }
        };
    }

    public AuditIssue makeScanIssue(HttpRequestResponse messageInfo, List<ImageMetaDataIssueItem> issueItems) {

        return new AuditIssue() {

            public IssueItem getItem() {
                if (issueItems.isEmpty()) {
                    return null;
                } else {
                    return issueItems.get(0);
                }
            }

            final String ISSUE_BACKGROUND = "\r\n"
                + "<h4>Image meta data:</h4>"
                + "<ul>"
                + "Extract metadata."
                + "</ul>"
                + "<h4>Reference:</h4>"
                + "<ul>"
                + "  <li><a href=\"https://github.com/drewnoakes/metadata-extractor/\">https://github.com/drewnoakes/metadata-extractor/</a></li>"
                + "</ul>";

            @Override
            public String name() {
                String issueName = getIssueName();
                if (!"".equals(getItem().getType())) {
                    issueName += " " + "(" + getItem().getType() + ")";
                }
                return issueName;
            }

            @Override
            public String detail() {
                StringBuilder buff = new StringBuilder();
                buff.append("<h4>Image meta data:</h4>");
                for (ImageMetaDataIssueItem markItem : issueItems) {
                    buff.append("<div>");
                    buff.append("<strong>");
                    buff.append(HttpUtil.toHtmlEncode(markItem.getCategory()));
                    buff.append("</strong>");
                    buff.append("<table>");
                    for (ImageRowData row : markItem.getMetaRows()) {
                        buff.append("<tr>");
                            buff.append("<td>");
                            buff.append(HttpUtil.toHtmlEncode(row.getTag().getTagName()));
                            buff.append("</td>");
                            buff.append("<td>");
                            buff.append(HttpUtil.toHtmlEncode(row.getTag().getDescription()));
                            buff.append("</td>");
                        buff.append("</tr>");
                    }
                    buff.append("</table>");
                }
                return buff.toString();
            }

            @Override
            public String remediation() {
                return null;
            }

            @Override
            public HttpService httpService() {
                return messageInfo.request().httpService();
            }

            @Override
            public String baseUrl() {
                return messageInfo.request().url();
            }

            @Override
            public AuditIssueSeverity severity() {
                IssueItem item = getItem();
                return item.getServerity().toAuditIssueSeverity();
            }

            @Override
            public AuditIssueConfidence confidence() {
                IssueItem item = getItem();
                return item.getConfidence().toAuditIssueConfidence();
            }

            @Override
            public List<HttpRequestResponse> requestResponses() {
                return Arrays.asList(messageInfo);
            }

            @Override
            public AuditIssueDefinition definition() {
                return AuditIssueDefinition.auditIssueDefinition(name(), ISSUE_BACKGROUND, remediation(), severity());
            }

            @Override
            public List<Interaction> collaboratorInteractions() {
                return new ArrayList<>();
            }
        };
    }
}
