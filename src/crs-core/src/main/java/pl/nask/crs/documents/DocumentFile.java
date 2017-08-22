package pl.nask.crs.documents;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import pl.nask.crs.commons.utils.Validator;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentFile {

    private static final List<String> DEFAULT_TIFF_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("tiff", "tif"));
    private static final List<String> DEFAULT_PICTURE_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("jpg", "jpeg", "png", "bmp", "gif"));

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");

    private String fileName;

    private Date addDate;

    private DocumentFileType fileType;

    private Date modificationDate;

    public DocumentFile(String fileName, DocumentFileType documentFileType) {
        setFileName(fileName);
        setFileType(documentFileType);
    }

    public DocumentFile(String fileName, String type) {
        setFileName(fileName);
        setFileType(DocumentFileType.resolveType(type));
    }

    public String getFileName() {
        return fileName;
    }

    public static String getNormalizedFilenameWithAddDate(String fileName, Date addDate) {        
        if (!hasExtension(fileName)) {
            return UUID.nameUUIDFromBytes(fileName.getBytes()).toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(UUID.nameUUIDFromBytes(getNameWithoutExtension(fileName).getBytes()).toString());
            sb.append(".");
            sb.append(getFormatedAddDate(addDate));
            sb.append(".");
            sb.append(getExtension(fileName));
            return sb.toString();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        Validator.assertNotNull(modificationDate, "Modification date cannot be null");
        this.modificationDate = modificationDate;
    }

    public DocumentFileType getFileType() {
        return fileType;
    }

    public String getFileTypeAsString() {
        return fileType.toString();
    }

    public void setFileType(DocumentFileType fileType) {
        Validator.assertNotNull(fileType, "Document file type cannot be null");
        this.fileType = fileType;
    }

    public void setFileType(String fileType) {
        setFileType(DocumentFileType.valueOf(fileType));
    }

    public static boolean isTiff(String fileName) {
        String ext = getExtension(fileName).toLowerCase();
        return DEFAULT_TIFF_EXTENSIONS.contains(ext);
    }

    public static boolean isPicture(String fileName) {
        String ext = getExtension(fileName).toLowerCase();
        return DEFAULT_PICTURE_EXTENSIONS.contains(ext);
    }

    private static String getExtension(String fileName) {
        Validator.assertNotNull(fileName, "file name");
        if (!hasExtension(fileName)) return fileName;
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex + 1);
    }

    private static String getNameWithoutExtension(String fileName) {
        Validator.assertNotNull(fileName, "file name");
        if (!hasExtension(fileName)) return fileName;
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(0, dotIndex);
    }

    private static boolean hasExtension(String fileName) {
        Validator.assertNotNull(fileName, "file name");
        int dotIndex = fileName.lastIndexOf('.');
        return ((dotIndex > 0) && (dotIndex + 1 < fileName.length()));
    }

    public boolean isFaxType() {
        return getFileType().isFaxType();
    }

    public boolean isAttachmentType() {
        return getFileType().isAttachmentType();
    }

    public boolean isPaperType() {
        return getFileType().isPaperType();
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public static String getFormatedAddDate(Date addDate) {
        return (addDate == null) ? "" : dateFormat.format(addDate);
    }

    public Date getAddDate() {
        return addDate;
    }
}
