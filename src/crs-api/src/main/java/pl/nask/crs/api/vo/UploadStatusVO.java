package pl.nask.crs.api.vo;

import pl.nask.crs.documents.UploadStatus;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum UploadStatusVO {
    OK,
    FILE_TOO_BIG,
    WRONG_FILE_TYPE,
    UNKNOWN_DOMAINNAME,
    UPLOAD_NOT_BY_BILL_C;

    public static UploadStatusVO fromUploadStatus(UploadStatus status) {
        switch (status) {
        case FILE_TOO_BIG:
            return FILE_TOO_BIG;
        case WRONG_FILE_TYPE:
            return WRONG_FILE_TYPE;
        case UNKNOWN_DOMAINNAME:
            return UNKNOWN_DOMAINNAME;
        case UPLOAD_NOT_BY_BILL_C:
            return UPLOAD_NOT_BY_BILL_C;
        case OK:
            return OK;
        default:
            throw new IllegalArgumentException("Unknown UploadStatus " + status.name());
        }
    }
}
