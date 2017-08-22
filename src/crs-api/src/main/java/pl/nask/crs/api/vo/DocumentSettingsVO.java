package pl.nask.crs.api.vo;

import pl.nask.crs.app.document.DocumentSettings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentSettingsVO {

    private int documentSizeLimit;
    private int documentCountLimit;
    private List<String> documentAllowedTypes;

    public DocumentSettingsVO() {}

    public DocumentSettingsVO(DocumentSettings settings) {
        this.documentSizeLimit = settings.getDocumentSizeLimit();
        this.documentCountLimit = settings.getDocumentCountLimit();
        this.documentAllowedTypes = settings.getAllowedTypes();
    }

    public int getDocumentSizeLimit() {
        return documentSizeLimit;
    }

    public int getDocumentCountLimit() {
        return documentCountLimit;
    }

    public List<String> getDocumentAllowedTypes() {
        return documentAllowedTypes;
    }
}
