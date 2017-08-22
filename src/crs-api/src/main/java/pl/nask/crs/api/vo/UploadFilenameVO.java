package pl.nask.crs.api.vo;

import pl.nask.crs.documents.UploadFilename;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class UploadFilenameVO implements UploadFilename {
    private String filesystemName;
    private String userFilename;

    public UploadFilenameVO() {
    }

    public UploadFilenameVO(String filesystemName, String userFilename) {
        this.filesystemName = filesystemName;
        this.userFilename = userFilename;
    }

    public String getFilesystemName() {
        return filesystemName;
    }

    public void setFilesystemName(String filesystemName) {
        this.filesystemName = filesystemName;
    }

    public String getUserFilename() {
        return userFilename;
    }

    public void setUserFilename(String userFilename) {
        this.userFilename = userFilename;
    }
}
