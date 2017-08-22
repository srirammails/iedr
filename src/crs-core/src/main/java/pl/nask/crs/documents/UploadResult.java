package pl.nask.crs.documents;

public class UploadResult {
    private String documentName;
    private UploadStatus status;

    public UploadResult(String filename, UploadStatus status) {
        documentName = filename;
        this.status = status;
    }

    public String getDocumentName() {
        return documentName;
    }

    public UploadStatus getStatus() {
        return status;
    }
}
