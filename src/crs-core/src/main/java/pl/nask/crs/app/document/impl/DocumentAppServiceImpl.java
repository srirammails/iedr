package pl.nask.crs.app.document.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.document.DocumentSettings;
import pl.nask.crs.app.document.WrongMailFormatException;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.DocumentUpload;
import pl.nask.crs.documents.UploadFilename;
import pl.nask.crs.documents.UploadResult;
import pl.nask.crs.documents.UploadStatus;
import pl.nask.crs.documents.email.DocumentParameters;
import pl.nask.crs.documents.exception.DocumentFileMovingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.DocumentsCountOutOfBoundsException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.exception.UnknownDomainException;
import pl.nask.crs.documents.exception.UploadNotByBillCException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;
import pl.nask.crs.documents.exception.WrongFileSizeException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentAppServiceImpl implements DocumentAppService {

    private static final Logger LOG = Logger.getLogger(DocumentAppServiceImpl.class);
    public static final String DOMAINS_PREFIX = "Domains: ";

    private final ApplicationConfig appConfig;
    private final DocumentService documentService;
    private final AccountSearchService accountSearchService;
    private final EmailTemplateSender emailTemplateSender;
    private final TicketSearchService ticketSearchService;
    private final TicketService ticketService;

    public DocumentAppServiceImpl(ApplicationConfig appConfig, DocumentService documentService, AccountSearchService accountSearchService, EmailTemplateSender emailTemplateSender, TicketSearchService ticketSearchService, TicketService ticketService) {
        this.appConfig = appConfig;
        this.documentService = documentService;
        this.accountSearchService = accountSearchService;
        this.emailTemplateSender = emailTemplateSender;
        this.ticketSearchService = ticketSearchService;
        this.ticketService = ticketService;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentSettings getDocumentSettings(AuthenticatedUser user) throws AccessDeniedException {
        return new DocumentSettings(
                appConfig.getDocumentUploadSizeLimit(),
                appConfig.getDocumentUploadCountLimit(),
                appConfig.getDocumentAllowedTypes());
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Document> getNewDocuments(AuthenticatedUser user, int offset, int limit, List<SortCriterion> sortBy) throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        return documentService.getNewDocuments(offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Document> findDocuments(AuthenticatedUser user, DocumentSearchCriteria criteria, int offset, int limit, List<SortCriterion> orderBy) {
        return documentService.findDocuments(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public Document get(AuthenticatedUser user, Long id) {
        return documentService.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AuthenticatedUser user, Document document) throws EmailSendingException, DocumentGeneralException, NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException {
        documentService.add(document);
        if (document.getAccountNumber() != null) {
            Account acc = findAccount(document.getAccountNumber());
            String email = acc.getBillingContact().getEmail();
            String accountNicHandle = acc.getBillingContact().getNicHandle();
            String username = (user == null) ? null : user.getUsername();
            try {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.ADD_DOC.getId(),
                        new DocumentParameters(document, email, username, accountNicHandle));
            } catch (Exception e) {
                throw new EmailSendingException(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDocumentFile(AuthenticatedUser user, Document document) throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException, NoSuchDocumentException {
        documentService.deleteDocumentFile(document.getDocumentFile());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean documentFileExists(AuthenticatedUser user, Document document) throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        return documentService.documentFileExists(document);
    }

    @Override
    @Transactional(readOnly = true)
    public String getPath(AuthenticatedUser user, Document document) {
        return documentService.getPath(document);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthenticatedUser user, Document document) throws DocumentGeneralException {
        documentService.update(document);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UploadResult> handleUpload(AuthenticatedUser user, String sendingNH, List<? extends DocumentUpload> uploads, DocumentPurpose documentPurpose) throws DocumentGeneralException {
        return handleUpload(user, sendingNH, DocumentFileType.UPLOAD_VIA_CONSOLE, uploads, documentPurpose);
    }

    private void updateAffectedTickets(AuthenticatedUser user, DocumentFileType sourceOfUpload, Collection<String> affectedDomains) {
        StringBuilder remarkBuilder = new StringBuilder("Documents uploaded");
        if (sourceOfUpload == DocumentFileType.UPLOAD_VIA_CONSOLE)
            remarkBuilder.append(" via console");
        else if (sourceOfUpload == DocumentFileType.UPLOAD_VIA_MAIL)
            remarkBuilder.append(" via mail");

        for (String domainName : affectedDomains) {
            final TicketSearchCriteria criteria = new TicketSearchCriteria();
            criteria.setDomainName(domainName);
            List<Ticket> tickets = ticketSearchService.findAll(criteria, null);
            for (Ticket t : tickets) {
                try {
                    AdminStatus newStatus;
                    final DomainOperation.DomainOperationType ticketType = t.getOperation().getType();
                    if (ticketType == DomainOperation.DomainOperationType.REG ||
                        ticketType == DomainOperation.DomainOperationType.MOD) {
                        newStatus = AdminStatusEnum.RENEW;
                    } else {
                        // don't change admin status, only set remark
                        newStatus = t.getAdminStatus();
                    }
                    ticketService.updateAdminStatus(t.getId(), newStatus, user.getUsername(), remarkBuilder.toString());
                } catch (TicketNotFoundException e) {
                    LOG.error("Ticket "+t.getId()+" not found in renewing after document upload", e);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleMailUpload(AuthenticatedUser user, String replyTo, String mailContent, List<? extends UploadFilename> attachmentFilenames) {
        DocumentPurpose documentPurpose;
        List<String> domainNames;
        EmailTemplateNamesEnum emailTemplate;
        try {
            MailContent parsedMail = parseEmailUpload(mailContent);
            documentPurpose = parsedMail.getPurpose();
            domainNames = parsedMail.getDomainNames();
            emailTemplate = EmailTemplateNamesEnum.DOCUMENT_UPLOAD_SUCCESSFULL;
        } catch (WrongMailFormatException e) {
            documentPurpose = null;
            domainNames = Collections.emptyList();
            emailTemplate = EmailTemplateNamesEnum.DOCUMENT_MAIL_PARSE_FAILURE;
        }

        List<DocumentUpload> docUploads = new ArrayList<DocumentUpload>(attachmentFilenames.size());
        for (UploadFilename attachmentFilename : attachmentFilenames) {
            docUploads.add(new DocumentUploadImpl(attachmentFilename.getFilesystemName(), domainNames));
        }

        try {
            List<UploadResult> result = handleUpload(user, null, DocumentFileType.UPLOAD_VIA_MAIL, docUploads, documentPurpose);
            sendEmail(emailTemplate, replyTo, prepareInfo(domainNames, result, attachmentFilenames));
        } catch (DocumentsCountOutOfBoundsException e) {
            LOG.info("Upload of document by mail failed due to wrong number of documents: " + docUploads.size());
            sendEmail(EmailTemplateNamesEnum.DOCUMENT_UPLOAD_FAILURE, replyTo, e.getMessage());
        } catch (Exception e) {
            LOG.error("Upload of document by mail failed", e);
            sendEmail(EmailTemplateNamesEnum.DOCUMENT_UPLOAD_FAILURE, replyTo, "General server error occured, please contact IEDR.");
        }
    }

    private List<UploadResult> handleUpload(AuthenticatedUser user, String sendingNH, DocumentFileType sourceOfUpload, List<? extends DocumentUpload> uploads, DocumentPurpose documentPurpose) throws DocumentGeneralException {
        List<UploadResult> result = new ArrayList<UploadResult>(uploads.size());
        try {
            if (uploads.size() == 0) {
                throw new DocumentsCountOutOfBoundsException("No files were uploaded, required at least one attachment");
            }
            if (uploads.size() > appConfig.getDocumentUploadCountLimit()) {
                throw new DocumentsCountOutOfBoundsException("Too many files uploaded, max allowed is " + Integer.toString(appConfig.getDocumentUploadCountLimit()));
            }
            Set<String> domainNames = new HashSet<String>();
            for (DocumentUpload upload : uploads) {
                UploadStatus status;
                try {
                    final DocumentFile docFile = new DocumentFile(upload.getFilename(), sourceOfUpload);
                    documentService.uploadedDocumentFile(user, sendingNH, documentPurpose, docFile, upload.getDomains());
                    domainNames.addAll(upload.getDomains());
                    status = UploadStatus.OK;
                } catch (UnknownDomainException e) {
                    status = UploadStatus.UNKNOWN_DOMAINNAME;
                } catch (UploadNotByBillCException e) {
                    status = UploadStatus.UPLOAD_NOT_BY_BILL_C;
                } catch (WrongFileSizeException e) {
                    status = UploadStatus.FILE_TOO_BIG;
                } catch (WrongFaxFileExtensionException e) {
                    status = UploadStatus.WRONG_FILE_TYPE;
                }

                result.add(new UploadResult(upload.getFilename(), status));
            }
            updateAffectedTickets(user, sourceOfUpload, domainNames);
            return result;
        } finally {
            final Predicate<UploadResult> statusIsOk = new Predicate<UploadResult>() {
                @Override
                public boolean apply(UploadResult uploadResult) {
                    return uploadResult.getStatus() == UploadStatus.OK;
                }
            };
            final Function<UploadResult, String> extractFilenameFromResult = new Function<UploadResult, String>() {
                @Override
                public String apply(UploadResult uploadResult) {
                    return uploadResult.getDocumentName();
                }
            };
            final Function<DocumentUpload, String> extractFilenameFromUpload = new Function<DocumentUpload, String>() {
                @Override
                public String apply(DocumentUpload documentUpload) {
                    return documentUpload.getFilename();
                }
            };

            Iterable<String> notSuccessfullyUploadedFiles = Iterables.filter(
                Iterables.transform(uploads, extractFilenameFromUpload),
                Predicates.not(Predicates.in(
                    Collections2.transform(
                        Collections2.filter(result, statusIsOk),
                        extractFilenameFromResult
                    )
                ))
            );
            for (String filename : notSuccessfullyUploadedFiles) {
                final DocumentFile docFile = new DocumentFile(filename, sourceOfUpload);
                try {
                    documentService.deleteFile(docFile);
                } catch (DocumentGeneralException e) {
                    LOG.error("Error cleaning failed uploaded file " + filename, e);
                }
            }
        }
    }

    private MailContent parseEmailUpload(String mailContent) throws WrongMailFormatException {
        List<String> domainNames = new ArrayList<String>();
        for (String line : mailContent.split("\n")) {
            if (line.startsWith(DOMAINS_PREFIX)) {
                String domainsString = line.substring(DOMAINS_PREFIX.length());
                for (String domain: domainsString.split(",")) {
                    domainNames.add(StringUtils.trim(StringUtils.strip(domain)));
                }
            }
        }
        if (domainNames.isEmpty())
            throw new WrongMailFormatException();
        return new MailContent(domainNames, DocumentPurpose.MISCELLANEOUS);
    }

    private static class DocumentUploadImpl implements DocumentUpload {

        private final String filename;
        private final List<String> domains;

        public DocumentUploadImpl(String filename, List<String> domains) {
            this.filename = filename;
            this.domains = domains;
        }
        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public List<String> getDomains() {
            return domains;
        }
    }

    private void sendEmail(EmailTemplateNamesEnum emailEnum, String replyTo, String info) {
        try {
            MapBasedEmailParameters params = new MapBasedEmailParameters();
            params.set(ParameterNameEnum.CREATOR_C_EMAIL, replyTo);
            params.set(ParameterNameEnum.INFO, info);
            emailTemplateSender.sendEmail(emailEnum.getId(), params);
        } catch (Exception e) {
            LOG.error("Error sending email", e);
        }
    }

    private String prepareInfo(List<String> domains, List<UploadResult> results, List<? extends UploadFilename> uploadFilenames) {
        Map<String, String> systemToUserFilename = new HashMap<String, String>();
        for (UploadFilename uploadFilename : uploadFilenames) {
            systemToUserFilename.put(uploadFilename.getFilesystemName(), uploadFilename.getUserFilename());
        }
        StringBuilder result = new StringBuilder();
        String pastVerb = results.size() > 1 ? "were" : "was";
        result.append(results.size()).append(" ").append(pluralize("document", results.size())).append(" " + pastVerb + " uploaded.").append("\n");

        if (!domains.isEmpty()) {
            List<String> wrappedDomains = new ArrayList<String>();
            for (String domain : domains) wrappedDomains.add("\"" + domain + "\"");
            result.append("Found domains to which documents should be assigned: ").append(StringUtils.join(wrappedDomains, ", ")).append("\n\n");
        }

        result.append("Result of document uploads:\n");
        for (UploadResult uploadResult : results) {
            String filename = systemToUserFilename.get(uploadResult.getDocumentName());
            result.append(filename).append(": ").append(humanize(uploadResult.getStatus())).append("\n");
        }
        return result.toString();
    }

    private String humanize(UploadStatus status) {
        switch (status) {
            case OK:
                return "OK, file saved";
            case FILE_TOO_BIG:
                return "File too big, discarded";
            case WRONG_FILE_TYPE:
                return "Wrong file type, discarded";
            case UNKNOWN_DOMAINNAME:
                return "Unknown domain name, file is discarded";
            default:
            case UPLOAD_NOT_BY_BILL_C:
                // impossible at this step of handling email
                return "Unknown error, file is discarded";
        }
    }

    private String pluralize(String base, int count) {
        return base + (count == 1 ? "" : "s");
    }
    private static class MailContent {
        private final List<String> domainNames;
        private final DocumentPurpose purpose;

        private MailContent(List<String> domainNames, DocumentPurpose purpose) {
            this.domainNames = domainNames;
            this.purpose = purpose;
        }

        public List<String> getDomainNames() {
            return domainNames;
        }

        public DocumentPurpose getPurpose() {
            return purpose;
        }
    }

    private Account findAccount(Long account)
            throws DocumentGeneralException {
        Account acc = accountSearchService.getAccount(account);

        if (acc == null)
            throw new DocumentGeneralException(
                    "Account not found for account number: " + account);

        if (!acc.isActive())
            throw new DocumentGeneralException("Account not active: " + acc);

        return acc;
    }
}
