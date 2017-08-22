package pl.nask.crs.app.document;

import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentUpload;
import pl.nask.crs.documents.UploadFilename;
import pl.nask.crs.documents.UploadResult;
import pl.nask.crs.documents.exception.DocumentFileMovingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.io.File;
import java.util.List;

public interface DocumentAppService {

    public DocumentSettings getDocumentSettings(AuthenticatedUser user) throws AccessDeniedException;

    LimitedSearchResult<Document> getNewDocuments(AuthenticatedUser user, int offset, int limit, List<SortCriterion> sortBy) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    LimitedSearchResult<Document> findDocuments(AuthenticatedUser user, DocumentSearchCriteria criteria, int offset, int limit, List<SortCriterion> orderBy);

    Document get(AuthenticatedUser user, Long id);

    void add(AuthenticatedUser user, Document document) throws EmailSendingException, DocumentGeneralException, NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException;

    void deleteDocumentFile(AuthenticatedUser user, Document document) throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException, NoSuchDocumentException;

    boolean documentFileExists(AuthenticatedUser user, Document document) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    String getPath(AuthenticatedUser user, Document document);

    void update(AuthenticatedUser user, Document document) throws DocumentGeneralException;

    List<UploadResult> handleUpload(AuthenticatedUser user, String sendingNH, List<? extends DocumentUpload> uploads, DocumentPurpose documentPurpose) throws DocumentGeneralException;

    void handleMailUpload(AuthenticatedUser user, String replyTo, String mailContent, List<? extends UploadFilename> attachmentFilenames);
}
