package pl.nask.crs.api.vo;

import pl.nask.crs.documents.DocumentPurpose;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum UploadPurposeVO {
    REGISTRATION,
    MODIFICATION;

    public DocumentPurpose asDocumentPurpose() {
        switch (this) {
            case REGISTRATION: return DocumentPurpose.REGISTRATION;
            case MODIFICATION: return DocumentPurpose.GENERAL_MOD;
            default: throw new IllegalArgumentException("Unknown upload purpose " + name());
        }
    }

}
