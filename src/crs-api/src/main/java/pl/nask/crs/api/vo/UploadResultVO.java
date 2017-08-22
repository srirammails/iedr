package pl.nask.crs.api.vo;

import pl.nask.crs.documents.UploadResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class UploadResultVO {
    private String documentName;
    private UploadStatusVO status;

    public UploadResultVO() {
    }

    public UploadResultVO(String documentName, UploadStatusVO status) {
        this.documentName = documentName;
        this.status = status;
    }

    public UploadResultVO(UploadResult uploadResult) {
        this(uploadResult.getDocumentName(), UploadStatusVO.fromUploadStatus(uploadResult.getStatus()));
    }

    public String getDocumentName() {
        return documentName;
    }

    public UploadStatusVO getStatus() {
        return status;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setStatus(UploadStatusVO status) {
        this.status = status;
    }
}
