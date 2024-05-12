[1mdiff --git a/libs/BurpExtensionCommons-v3.2.0.0-all.jar b/libs/BurpExtensionCommons-v3.2.0.0-all.jar[m
[1mindex c349fcc..b5d098a 100644[m
Binary files a/libs/BurpExtensionCommons-v3.2.0.0-all.jar and b/libs/BurpExtensionCommons-v3.2.0.0-all.jar differ
[1mdiff --git a/release/ImageMetaDataExtract-v1.1-all.jar b/release/ImageMetaDataExtract-v1.1-all.jar[m
[1mindex fb26665..612e872 100644[m
Binary files a/release/ImageMetaDataExtract-v1.1-all.jar and b/release/ImageMetaDataExtract-v1.1-all.jar differ
[1mdiff --git a/src/main/java/burp/BurpExtension.java b/src/main/java/burp/BurpExtension.java[m
[1mindex b9cc07c..4f5afc4 100644[m
[1m--- a/src/main/java/burp/BurpExtension.java[m
[1m+++ b/src/main/java/burp/BurpExtension.java[m
[36m@@ -17,6 +17,8 @@[m [mpublic class BurpExtension extends BurpExtensionImpl {[m
 [m
     private final static Logger logger = Logger.getLogger(BurpExtension.class.getName());[m
 [m
[32m+[m[32m    private final static java.util.ResourceBundle BUNDLE = java.util.ResourceBundle.getBundle("burp/resources/release");[m
[32m+[m
     private final HttpResponseEditorProvider responseMetaDataTab = new HttpResponseEditorProvider() {[m
 [m
         @Override[m
[36m@@ -29,7 +31,7 @@[m [mpublic class BurpExtension extends BurpExtensionImpl {[m
     @Override[m
     public void initialize(MontoyaApi api) {[m
         super.initialize(api);[m
[31m-        api().extension().setName("ImageMetaDataExtract");[m
[32m+[m[32m        api().extension().setName(BUNDLE.getString("projname"));[m
         api().userInterface().registerHttpResponseEditorProvider(this.responseMetaDataTab);[m
         ImageMetaDataSignature signature = new ImageMetaDataSignature();[m
         api().scanner().registerScanCheck(signature.getSignatureScan().passiveScanCheck());[m
[1mdiff --git a/src/main/java/metadata/ImageMetaData.java b/src/main/java/metadata/ImageMetaData.java[m
[1mindex d6cf498..30cadb8 100644[m
[1m--- a/src/main/java/metadata/ImageMetaData.java[m
[1m+++ b/src/main/java/metadata/ImageMetaData.java[m
[36m@@ -11,6 +11,8 @@[m [mimport java.io.IOException;[m
 import java.io.InputStream;[m
 import java.util.ArrayList;[m
 import java.util.Arrays;[m
[32m+[m[32mimport java.util.EnumSet;[m
[32m+[m[32mimport java.util.Iterator;[m
 import java.util.List;[m
 import java.util.Map;[m
 import java.util.TreeMap;[m
[36m@@ -97,33 +99,44 @@[m [mpublic class ImageMetaData {[m
         }[m
     }[m
 [m
[32m+[m[32m    private final static EnumSet<FileType> SUPPORT_FILE_TYPES = EnumSet.of([m
[32m+[m[32m            FileType.Jpeg,[m
[32m+[m[32m            FileType.Tiff,[m
[32m+[m[32m            FileType.Arw,[m
[32m+[m[32m            FileType.Cr2,[m
[32m+[m[32m            FileType.Nef,[m
[32m+[m[32m            FileType.Orf,[m
[32m+[m[32m            FileType.Rw2,[m
[32m+[m[32m            FileType.Psd,[m
[32m+[m[32m            FileType.Png,[m
[32m+[m[32m            FileType.Bmp,[m
[32m+[m[32m            FileType.Gif,[m
[32m+[m[32m            FileType.Ico,[m
[32m+[m[32m            FileType.Pcx,[m
[32m+[m[32m            FileType.WebP,[m
[32m+[m[32m            FileType.Raf,[m
[32m+[m[32m            FileType.Avi,[m
[32m+[m[32m            FileType.Wav,[m
[32m+[m[32m            FileType.QuickTime,[m
[32m+[m[32m            FileType.Mp4,[m
[32m+[m[32m            FileType.Mp3,[m
[32m+[m[32m            FileType.Eps,[m
[32m+[m[32m            FileType.Heif[m
[32m+[m[32m    );[m
[32m+[m
     public static boolean supportFileType(FileType fileType) {[m
[31m-        switch (fileType) {[m
[31m-            case Jpeg:[m
[31m-            case Tiff:[m
[31m-            case Arw:[m
[31m-            case Cr2:[m
[31m-            case Nef:[m
[31m-            case Orf:[m
[31m-            case Rw2:[m
[31m-            case Psd:[m
[31m-            case Png:[m
[31m-            case Bmp:[m
[31m-            case Gif:[m
[31m-            case Ico:[m
[31m-            case Pcx:[m
[31m-            case WebP:[m
[31m-            case Raf:[m
[31m-            case Avi:[m
[31m-            case Wav:[m
[31m-            case QuickTime:[m
[31m-            case Mp4:[m
[31m-            case Mp3:[m
[31m-            case Eps:[m
[31m-            case Heif:[m
[31m-                return true;[m
[32m+[m[32m        return SUPPORT_FILE_TYPES.contains(fileType);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static boolean supportMimeType(String mimeType) {[m
[32m+[m[32m        for (Iterator<FileType> ite = SUPPORT_FILE_TYPES.iterator(); ite.hasNext(); ) {[m
[32m+[m[32m            FileType fileType  = ite.next();[m
[32m+[m[32m            if (fileType.getMimeType().equals(mimeType)) {[m
[32m+[m[32m               return true;[m
[32m+[m[32m            }[m
         }[m
         return false;[m
     }[m
 [m
[32m+[m
 }[m
[1mdiff --git a/src/main/java/passive/signature/ImageMetaDataScan.java b/src/main/java/passive/signature/ImageMetaDataScan.java[m
[1mindex d1e9b3a..255884a 100644[m
[1m--- a/src/main/java/passive/signature/ImageMetaDataScan.java[m
[1m+++ b/src/main/java/passive/signature/ImageMetaDataScan.java[m
[36m@@ -22,7 +22,6 @@[m [mimport java.io.ByteArrayInputStream;[m
 import java.io.IOException;[m
 import java.util.ArrayList;[m
 import java.util.Arrays;[m
[31m-import java.util.Collection;[m
 import java.util.List;[m
 import java.util.Map;[m
 import java.util.logging.Level;[m
[36m@@ -64,7 +63,10 @@[m [mpublic class ImageMetaDataScan extends SignatureScanBase<ImageMetaDataIssueItem>[m
                             for (String key : metaGroup.keySet()) {[m
                                 List<ImageRowData> rows = metaGroup.get(key);[m
                                 /* Content-Type Mismatch */[m
[31m-                                if (wrapResponse.getContentMimeType() != null && !imageMeta.getFileMimeType().equals(wrapResponse.getContentMimeType())) {[m
[32m+[m[32m                                String mimeType = wrapResponse.getContentMimeType();[m
[32m+[m[32m                                if (mimeType != null &&[m
[32m+[m[32m                                    (!mimeType.equals(imageMeta.getFileMimeType()) ||[m
[32m+[m[32m                                    (imageMeta.getFileType() ==  FileType.Unknown && ImageMetaData.supportMimeType(mimeType)))) {[m
                                     List<ImageMetaDataIssueItem> issueList = new ArrayList<>();[m
                                     final StringBuilder detail = new StringBuilder();[m
                                     detail.append("<h4>Content-Type mismatch of image:</h4>");[m
[36m@@ -72,13 +74,13 @@[m [mpublic class ImageMetaDataScan extends SignatureScanBase<ImageMetaDataIssueItem>[m
                                     detail.append("<p>Response Content-Type Header:</p>");[m
                                     detail.append("<p>");[m
                                     detail.append("<code>");[m
[31m-                                    detail.append(wrapResponse.getContentMimeType());[m
[32m+[m[32m                                    detail.append(HttpUtil.toHtmlEncode(mimeType));[m
                                     detail.append("</code>");[m
                                     detail.append("</p>");[m
                                     detail.append("<p>Image of MimeType:</p>");[m
                                     detail.append("<p>");[m
                                     detail.append("<code>");[m
[31m-                                    detail.append(imageMeta.getFileMimeType());[m
[32m+[m[32m                                    detail.append(HttpUtil.toHtmlEncode(imageMeta.getFileMimeType()));[m
                                     detail.append("</code>");[m
                                     detail.append("</p>");[m
 [m
