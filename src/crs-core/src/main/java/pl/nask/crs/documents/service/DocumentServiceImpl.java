package pl.nask.crs.documents.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.documents.DocumentUpload;
import pl.nask.crs.documents.IncomingDocumentsManager;
import pl.nask.crs.documents.dao.ibatis.DocumentDAO;
import pl.nask.crs.documents.exception.DocumentFileMovingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.exception.UnknownDomainException;
import pl.nask.crs.documents.exception.UploadNotByBillCException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;
import pl.nask.crs.documents.exception.WrongFileSizeException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

/**
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 */
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO dao;
    private final IncomingDocumentsManager incomingDocumentsManager;
    private final AccountSearchService accountSearchService;
    private final ApplicationConfig appConfig;
    private final DomainSearchService domainSearchService;
    private final TicketSearchService ticketSeachService;

    public DocumentServiceImpl(DocumentDAO dao,
                               IncomingDocumentsManager incomingDocumentsManager,
                               AccountSearchService accountSearchService,
                               ApplicationConfig appConfig,
                               DomainSearchService domainSearchService,
                               TicketSearchService ticketSearchService) {
        Validator.assertNotNull(dao, "Document dao cannot be null.");
        Validator.assertNotNull(incomingDocumentsManager,
                "Documents manager cannot be null.");
        Validator.assertNotNull(accountSearchService, "Account search service");
        Validator.assertNotNull(appConfig, "Application config");
        this.dao = dao;
        this.incomingDocumentsManager = incomingDocumentsManager;
        this.accountSearchService = accountSearchService;
        this.appConfig = appConfig;
        this.domainSearchService = domainSearchService;
        this.ticketSeachService = ticketSearchService;
    }

    @Override
    public LimitedSearchResult<Document> getNewDocuments(int offset, int limit, List<SortCriterion> sortBy)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        List<DocumentFile> incomingDocuments = incomingDocumentsManager.getNewDocumentFiles();
        int filesCount = incomingDocumentsManager.getNewDocumentFilesCount();
        List<Document> result = new ArrayList<Document>(incomingDocuments.size());
        for (DocumentFile documentFile : incomingDocuments) {
            Document document = new Document(documentFile, null, "", null,
                    new ArrayList<String>(), null);
            document.setDate(documentFile.getModificationDate());
            result.add(document);
        }
        if (sortBy != null && sortBy.size() != 0)
            Collections.sort(result, new DocumentComparator(sortBy));

        int calculatedLimit = Math.min(limit, filesCount);
        List<Document> resultLimited = new ArrayList<Document>(calculatedLimit);

        for (int i = 0; i < calculatedLimit; i++) {
            int index = offset + i;
            if (index < result.size()) {
                resultLimited.add(result.get(index));
            }
        }

        return new LimitedSearchResult<Document>(null, sortBy, calculatedLimit, offset,
                resultLimited, filesCount);
    }

    @Override
    public LimitedSearchResult<Document> findDocuments(
            DocumentSearchCriteria criteria, int offset, int limit, List<SortCriterion> orderBy) {
        return dao.find(criteria, offset, limit, orderBy);
    }

    @Override
    public Document get(Long id) {
        return dao.get(id);
    }

    @Override
    public void deleteDocumentFile(DocumentFile document)
            throws DocumentFileMovingException, NoSuchDirectoryException,
            WrongFaxFileExtensionException, NoSuchDocumentException {
        Validator.assertNotNull(document, "DocumentFile");
        incomingDocumentsManager.deleteDocumentFile(document);
    }

    @Override
    public void deleteFile(DocumentFile docFile) throws NoSuchDirectoryException, DocumentFileMovingException, NoSuchDocumentException {
        incomingDocumentsManager.deleteFile(docFile);
    }

    @Override
    public String getPath(Document document) {
        Validator.assertNotNull(document, "Document");
        Validator.assertNotNull(document.getDocumentFile(), "Document file");
        DocumentFileType docFileType = document.getDocumentFile().getFileType();
        Validator.assertNotNull(docFileType, "Document file type");
        return incomingDocumentsManager.getDirMapping(docFileType);
    }

    @Override
    public boolean documentFileExists(Document document)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        Validator.assertNotNull(document, "Document");
        Validator.assertNotNull(document.getDocumentFile(), "Document file");

        return incomingDocumentsManager.documentFileExists(document
                .getDocumentFile());
    }

    @Override
    public void update(Document document) throws DocumentGeneralException {
        Validator.assertNotNull(document, "Document");
        DocumentFile dFile = document.getDocumentFile();
        Validator.assertNotNull(dFile, "Document file");
        DocumentFileType dFileType = dFile.getFileType();
        Validator.assertNotNull(dFileType, "Document file type");
        if (!dFileType.isAssigned())
            throw new DocumentGeneralException("Document is not assigned.");
        dao.update(document);
    }

    @Override
    public void add(Document document) throws DocumentGeneralException {
        Validator.assertNotNull(document, "Document");
        DocumentFile dFile = document.getDocumentFile();
        Validator.assertNotNull(dFile, "Document file");
        DocumentFileType dFileType = dFile.getFileType();
        Validator.assertNotNull(dFileType, "Document file type");
        if (dFileType.isAssigned())
            throw new DocumentGeneralException("Document is assigned.");

        /*
         * bug #1055 documentFile filename must be maximum 50 chars long. This
         * includes file extension, date and the date separator. file extension
         * takes 4 chars + '.' == 5 chars, date (without ms) takes 15 chars +
         * '.'. This leaves 29 chars for the filename without an extension. The
         * solution is to generate filenames which are shorter than the limit
         * (using UUID). In this case DocumentFile, IncomingDocumentsManager and
         * InternalDocumentIBatisDAO must be modified.
         */
        document.addTimeToFileName();
        dao.create(document);
        incomingDocumentsManager.markDocumentFileAsAssigned(document
                .getDocumentFile());
    }

    @Override
    public void uploadedDocumentFile(AuthenticatedUser user, String sendingNH, DocumentPurpose purpose, DocumentFile docFile, List<String> domainNames) throws DocumentGeneralException {
        File documentFile = incomingDocumentsManager.getFileByDocument(docFile);
        validateDocumentFile(documentFile, domainNames, sendingNH);

        if (purpose != null && !domainNames.isEmpty()) {
            OpInfo op = new OpInfo(user);
            AccountSearchCriteria criteria = new AccountSearchCriteria();
            criteria.setNicHandle(op.getActorName());
            List<Account> accounts = accountSearchService.getAccounts(criteria);
            Long accountNumber = null;
            if (accounts.size() == 1)
                accountNumber = accounts.get(0).getId();

            Document doc = new Document(docFile, purpose, op.getActorNameForRemark(), accountNumber, domainNames, op.getActorName());
            add(doc);
        } else {
            incomingDocumentsManager.markFileAs(DocumentFileType.ATTACHMENT_NEW, documentFile);
        }
    }

    private void validateDocumentFile(File documentFile, List<String> domainNames, String username) throws WrongFaxFileExtensionException, WrongFileSizeException, UnknownDomainException, UploadNotByBillCException {
        List<String> allowedExtensions = appConfig.getDocumentAllowedTypes();
        Integer maxAllowedFileSize = appConfig.getDocumentUploadSizeLimit();

        String filename = documentFile.getName();
        String fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new WrongFaxFileExtensionException(fileExtension + " is not allowed. Allowed extensions " + CollectionUtils.toString(allowedExtensions, ", "));
        }
        final long filesize = documentFile.length();
        if (filesize > maxAllowedFileSize) {
            throw new WrongFileSizeException("File is too big, received " + Long.toString(filesize) + " bytes, but max allowed is " + maxAllowedFileSize + " bytes");
        }

        for (String domainName : domainNames) {
            String billC = null;
            try {
                Domain domain = domainSearchService.getDomain(domainName);
                billC = domain.getBillingContactNic();
            } catch (DomainNotFoundException e) {
                TicketSearchCriteria criteria = new TicketSearchCriteria();
                criteria.setDomainName(domainName);
                LimitedSearchResult<Ticket> tickets = ticketSeachService.find(criteria, 0, 1, null);
                if (tickets.getResults().size() == 1) {
                    Ticket t = tickets.getResults().get(0);
                    billC = t.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
                }
            }
            if (billC == null) {
                throw new UnknownDomainException();
            }
            if (username != null && !billC.equals(username))
                throw new UploadNotByBillCException();
        }
    }


    static class DocumentComparator implements Comparator<Document> {

        private SortCriterion sortBy;

        public DocumentComparator(List<SortCriterion> sortBy) {
            this.sortBy = sortBy.get(0);
        }
        @Override
        public int compare(Document o1, Document o2) {
            if(sortBy.isAscending())
                return o1.getDate().compareTo(o2.getDate());
            else
                return o2.getDate().compareTo(o1.getDate());
        }
    }

}
