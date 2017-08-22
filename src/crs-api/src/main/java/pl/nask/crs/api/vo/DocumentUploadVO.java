package pl.nask.crs.api.vo;

import pl.nask.crs.documents.DocumentUpload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class DocumentUploadVO implements DocumentUpload {

    @XmlElement(required = true)
    private String filename;
    @XmlElement(required = true)
    private List<String> domains;

    public DocumentUploadVO() {
    }

    public DocumentUploadVO(String filename, List<String> domains) {
        this.filename = filename;
        this.domains = domains;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }
}
