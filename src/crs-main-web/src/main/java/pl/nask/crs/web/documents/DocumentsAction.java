package pl.nask.crs.web.documents;

import com.opensymphony.xwork2.ActionContext;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;

import java.util.*;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentsAction extends GenericSearchAction<Document, DocumentSearchCriteria> {

    private static final String INCOMING = "incoming";
    private static final String ASSIGNED = "assigned";
    private static final String VIEW = "view";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String SEARCH = "search";
    private static final String LIST = "list";
    private static final String INPUT = "input";
    private static final String NAME_UPDATE = "nameUpdate";

    private DocumentAppService documentsService;
    private AccountSearchService accountSearchService;

    private DocumentWrapper dw;

    private boolean showAll;

    public DocumentsAction(final DocumentAppService documentService,
                           AccountSearchService accountSearchService) {
        super(new AppSearchService<Document, DocumentSearchCriteria>(){
            public LimitedSearchResult<Document> search(AuthenticatedUser user, DocumentSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return documentService.findDocuments(user, criteria, (int) offset, (int) limit, orderBy);
            }
        });
        Validator.assertNotNull(documentService, "Documents service");
        Validator.assertNotNull(documentService, "Account Search Service");
        this.documentsService = documentService;
        this.accountSearchService = accountSearchService;
    }

    public String incoming() throws Exception {
        LimitedSearchResult<Document> result = documentsService
                .getNewDocuments(getUser(),
                        (getTableParams().getPage() - 1) * getPageSize(), getPageSize(), getTableParams().createSortingCriteria(null));
        paginatedResult = new TicketsPaginatedList<Document>(result
                .getResults(), (int) result.getTotalResults(),
                getTableParams(), getPageSize());
        return INCOMING;
    }

    public String assigned() throws Exception {
        search();
        return ASSIGNED;
    }

    public String view() throws Exception {
        if (dw.getId() != null) {
            Document doc = documentsService.get(getUser(), dw.getId());
            if (doc == null)
                throw new NoSuchDocumentException("Document id: " + dw.getId());
            dw.setDocument(doc);
        } else {
            if (!documentsService.documentFileExists(getUser(), dw.getDocument()))
                throw new NoSuchDocumentException("Document with name: "
                        + dw.getFileName() + " not exists.");
        }
        return VIEW;
    }

    public String update() throws Exception {
        if (!validateDomainsNames("document.domainNames")) {
            return INPUT;
        } else {
            updateWraperWithCreatorId();
            documentsService.add(getUser(), dw.getDocument());
            return UPDATE;
        }
    }

    private void updateWraperWithCreatorId() {
        dw.setCreatorNicHandleId(getUser().getUsername());
    }

    public String nameUpdate() throws Exception  {
        if (!validateDomainsNames("document.domainNames")) {
            String currentDomains = dw.getDomainNames();
            Document doc = documentsService.get(getUser(), dw.getId());
            if (doc == null)
                throw new NoSuchDocumentException("Document id: " + dw.getId());
            if (currentDomains == null)
                currentDomains = "";
            doc.setDomains(Arrays.asList(currentDomains));
            dw.setDocument(doc);            
            return INPUT;
        } else {
            documentsService.update(getUser(), dw.getDocument());
            return NAME_UPDATE;
        }
    }

    public String list() throws Exception {
        paginatedResult = performSearch(getDocumentSearchCriteria(), getTableParams(), 30);
        return LIST;
    }

    public String delete() throws Exception {
        documentsService.deleteDocumentFile(getUser(), dw.getDocument());
        return DELETE;
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsForDocuments();
    }

    public List<String> getPurposes() {
        List<String> result = new ArrayList<String>(DocumentPurpose.values().length);
        for (DocumentPurpose purpose : DocumentPurpose.values()) {
            result.add(purpose.getValue());
        }
        return result;
    }

    public List<Account> getDocAccount() {
        if (dw.getDocument().getAccountNumber() == null)
            return Arrays.asList(new Account(-1, "[NONE]"));

        Account acc = accountSearchService.getAccount(dw.getAccount()
                .longValue());
        if (acc == null)
            return Arrays.asList(new Account(-1, "[NONE]"));

        return Arrays.asList(acc);
    }

    public List<String> getDocPurpose() {
        return (dw != null) ? Arrays.asList(dw.getDocPurpose()) : Arrays
                .asList("Unknown");
    }

    public String getDocumentSource() {
        if (dw == null)
            return "";
        return dw.getDocSource();
    }

    public void setDocument(DocumentWrapper dw) {
        this.dw = dw;
    }

    public DocumentWrapper getDocument() {
        return dw;
    }

    public DocumentSearchCriteria getDocumentSearchCriteria() {
        return getSearchCriteria();
    }

    public void setDocumentSearchCriteria(
            DocumentSearchCriteria documentSearchCriteria) {
        setSearchCriteria(documentSearchCriteria);
    }

    public String getPath() {
        return documentsService.getPath(getUser(), dw.getDocument());
    }

    public boolean documentFileExists() throws Exception {
        return documentsService.documentFileExists(getUser(), dw.getDocument());
    }

    @Override
    protected DocumentSearchCriteria createSearchCriteria() {
        return new DocumentSearchCriteria();
    }
    protected boolean validateDomainsNames(String fieldName) {
        String fieldValue = ActionContext.getContext().getValueStack().findString(fieldName);
        if(fieldValue == null) {
            addFieldError(fieldName, "You must enter domain name (or comma-separated domain names list) this document refers to");
            return false; 
        }
        boolean flag = true;
        String[] dArray = fieldValue.split(",");
        if(dArray.length == 0) {
            addFieldError(fieldName, "You must enter domain name (or comma-separated domain names list) this document refers to");
            return false; 
        }
        for (String d : dArray) {
            String domain = d.trim();
            if (domain.length() > 66) {
                addFieldError(fieldName, "Invalid domain length: "+domain);
                flag = false;
            }
        }
        if (dArray.length > 200) {
            addFieldError(fieldName, "Too many domains (" + dArray.length + "). Limit is 200.");
            flag = false;
        }
        return flag;
    }

    /*
    Feature #1219
     */
    protected void updateSearchCriteria(DocumentSearchCriteria searchCriteria) {
        if (super.getTableParams() == null)
            super.setTableParams(new TableParams());
        if (super.getTableParams().getSortBy() == null){
            super.getTableParams().setSortBy("date");
            super.getTableParams().setAscending(false);
        }
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

}
