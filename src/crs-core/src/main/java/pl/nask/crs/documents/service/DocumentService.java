package pl.nask.crs.documents.service;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.documents.DocumentUpload;
import pl.nask.crs.documents.exception.DocumentFileMovingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 * @author Piotr Tkaczyk
 */
public interface DocumentService {

    LimitedSearchResult<Document> getNewDocuments(int offset, int limit, List<SortCriterion> sortBy) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    LimitedSearchResult<Document> findDocuments(DocumentSearchCriteria criteria, int offset, int limit, List<SortCriterion> orderBy);
            
    public Document get(Long id);

    public void add(Document document) throws DocumentGeneralException, NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException;

    public void deleteDocumentFile(DocumentFile document) throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException, NoSuchDocumentException;

    void deleteFile(DocumentFile docFile) throws NoSuchDirectoryException, DocumentFileMovingException, NoSuchDocumentException;

    public boolean documentFileExists(Document document) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    public String getPath(Document document);

    void update(Document document) throws DocumentGeneralException;

    void uploadedDocumentFile(AuthenticatedUser user, String sendingNH, DocumentPurpose purpose, DocumentFile docFile, List<String> domainNames) throws DocumentGeneralException;
}
