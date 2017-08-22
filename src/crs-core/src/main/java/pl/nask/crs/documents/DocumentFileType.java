package pl.nask.crs.documents;

import pl.nask.crs.commons.utils.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Tkaczyk
 */
public enum DocumentFileType {
    FAX_NEW(false, "fax"), FAX_ASSIGNED(true, "fax"),
    ATTACHMENT_NEW(false, "attachment"), ATTACHMENT_ASSIGNED(true, "attachment"),
    PAPER_NEW(false, "paper"), PAPER_ASSIGNED(true, "paper"),
    UPLOAD_VIA_MAIL(false, "attachment"),
    UPLOAD_VIA_CONSOLE(false, "attachment");

    static Map<DocumentFileType, DocumentFileType> mappings;
    static Map<String, DocumentFileType> databaseTypes;

    static {
        mappings = new HashMap<DocumentFileType, DocumentFileType>();
        mappings.put(FAX_NEW, FAX_ASSIGNED);
        mappings.put(FAX_ASSIGNED, FAX_NEW);
        mappings.put(ATTACHMENT_NEW, ATTACHMENT_ASSIGNED);
        mappings.put(UPLOAD_VIA_MAIL, ATTACHMENT_ASSIGNED);
        mappings.put(UPLOAD_VIA_CONSOLE, ATTACHMENT_ASSIGNED);
        mappings.put(ATTACHMENT_ASSIGNED, ATTACHMENT_NEW);
        mappings.put(PAPER_NEW, PAPER_ASSIGNED);
        mappings.put(PAPER_ASSIGNED, PAPER_NEW);

        databaseTypes = new HashMap<String, DocumentFileType>();
        databaseTypes.put("fax", FAX_ASSIGNED);
        databaseTypes.put("attachment", ATTACHMENT_ASSIGNED);
        databaseTypes.put("paper", PAPER_ASSIGNED);
    }

    boolean assigned;
    String type;

    DocumentFileType(boolean assigned, String type) {
        this.assigned = assigned;
        this.type = type;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public boolean isFaxType() {
        return this == FAX_NEW ||
               this == FAX_ASSIGNED;
    }

    public boolean isAttachmentType() {
        return this == ATTACHMENT_NEW ||
               this == ATTACHMENT_ASSIGNED ||
               this == UPLOAD_VIA_CONSOLE ||
               this == UPLOAD_VIA_MAIL;
    }

    public boolean isPaperType() {
        return this == PAPER_NEW ||
               this == PAPER_ASSIGNED;
    }

    public String getFileTypeAsString() {
        return this.toString();
    }

    public String getType() {
        return type;
    }

    public static DocumentFileType getOppositType(DocumentFileType type) {
        return mappings.get(type);
    }

    public static DocumentFileType resolveType(String type) {
        Validator.assertNotEmpty(type, "Type cannot be null or empty.");
        String lowerCaseType = type.toLowerCase();
        if (!databaseTypes.containsKey(lowerCaseType))
            throw new IllegalArgumentException("Type: " + type + " undefined.");

        return databaseTypes.get(lowerCaseType);
    }
}
