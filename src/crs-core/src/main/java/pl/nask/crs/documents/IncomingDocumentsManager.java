package pl.nask.crs.documents;

import pl.nask.crs.documents.exception.*;

import java.io.File;
import java.util.List;

public interface IncomingDocumentsManager {

    List<DocumentFile> getNewDocumentFiles() throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    int getNewDocumentFilesCount() throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    void markDocumentFileAsAssigned(DocumentFile document) throws DocumentGeneralException, NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException;

    void markFileAs(DocumentFileType type, File file) throws NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException;

    boolean documentFileExists(DocumentFile documentFile) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    void deleteDocumentFile(DocumentFile documentFile) throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException, NoSuchDocumentException;

    void deleteFile(DocumentFile documentFile) throws NoSuchDirectoryException, DocumentFileMovingException, NoSuchDocumentException;

    String getDirMapping(DocumentFileType documentFileType);

    File getFileByDocument(DocumentFile filename) throws NoSuchDirectoryException, NoSuchDocumentException;

    // public only for testing
    File getDirectory(DocumentFileType documentFileType);

    // public only for testing
    DocumentFile getDocumentFile(String fileName, DocumentFileType docFileType) throws NoSuchDirectoryException, WrongFaxFileExtensionException;
}
