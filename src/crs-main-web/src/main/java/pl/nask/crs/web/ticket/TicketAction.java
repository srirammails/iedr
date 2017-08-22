package pl.nask.crs.web.ticket;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import org.apache.struts2.interceptor.validation.SkipValidation;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketInfo;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.utils.ValidationHelper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.services.FailureReasonFactory;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;
import pl.nask.crs.contacts.Contact;

/**
 * Base class for Ticket actions.
 *
 * @author Artur Gniadzik
 * @author Patrycja Wegrzynowicz
 */
public abstract class TicketAction extends AuthenticatedUserAwareAction {

    private Logger log = Logger.getLogger(this.getClass());

    // action results
    public static final String SEARCH = "search";
    public static final String REVISE = "revise";
    public static final String ALTER_STATUS = "alterstatus";
    public static final String ACCEPT = "accept";
    public static final String REJECT = "reject";
    public static final String EDIT = "edit";
    public static final String EMAIL_ERROR = "emailerror";

    public static final int TICKET_EXPIRATION_PERIOD = 27;

    // ticket id
    protected Long id;

    // factory for getting list of possible domain holder classes
    private EntityClassFactory entityClassFactory;

    // factory for failure reasons
    private FailureReasonFactory failureReasonFactory;

    private AccountSearchService accountSearchService;

    private DnsCheckService dnsCheckService;

    // admin status factory
    private Dictionary<Integer, AdminStatus> adminStatusFactory;

    // service allowing to look up for the ticket history
    private TicketAppService ticketAppService;

    protected TicketModel ticketModel;

    private TicketWrapper ticketWrapper;

    protected ValidationHelper validationHelper = new ValidationHelper(this);


    public List<EnchancedEntityClass> getTicketClasses() {
        return entityClassFactory.getEntries();
    }

    public void setDnsCheckService(DnsCheckService dnsCheckService) {
        this.dnsCheckService = dnsCheckService;
    }

    public Ticket getTicket() {
        return ticketModel.getTicket();
    }

    public TicketInfo getInfo() {
        return ticketModel.getAdditionalInfo();
    }

    public List<HistoricalObject<Ticket>> getTicketHistory() {
        return ticketModel.getHistory();
    }

    public TicketWrapper getTicketWrapper() {
        if (ticketWrapper == null)
            ticketWrapper = new TicketWrapper(getTicket(),
                    failureReasonFactory, accountSearchService,
                    entityClassFactory);
        return ticketWrapper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    * returns id of the edited ticket (needed when chaining to the
    * TicketSearchAction)
    */
    public long getPreviousTicketId() {
        return id;
    }

    public EntityClassFactory getEntityClassFactory() {
        return entityClassFactory;
    }

    public void setEntityClassFactory(EntityClassFactory entityClassFactory) {
        this.entityClassFactory = entityClassFactory;
    }

    public FailureReasonFactory getFailureReasonFactory() {
        return failureReasonFactory;
    }

    public void setFailureReasonFactory(
            FailureReasonFactory failureReasonFactory) {
        this.failureReasonFactory = failureReasonFactory;
    }

    public void setAccountSearchService(
            AccountSearchService accountSearchService) {
        this.accountSearchService = accountSearchService;
    }

    public Dictionary<Integer, AdminStatus> getAdminStatusFactory() {
        return adminStatusFactory;
    }

    public void setAdminStatusFactory(
            Dictionary<Integer, AdminStatus> adminStatusFactory) {
        this.adminStatusFactory = adminStatusFactory;
    }

    public TicketAppService getTicketAppService() {
        return ticketAppService;
    }

    public void setTicketAppService(TicketAppService ticketAppService) {
        this.ticketAppService = ticketAppService;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }

    public boolean isModification() {
        final DomainOperation.DomainOperationType type = getTicket().getOperation().getType();
        return type == DomainOperation.DomainOperationType.XFER || type == DomainOperation.DomainOperationType.MOD;
    }

    protected void refresh() throws Exception {
        fetchTicket();
    }

    public String input() throws Exception {
        fetchTicket();
        return super.input();
    }

    abstract protected TicketModel getTicketModel(long id)
            throws AccessDeniedException, TicketNotFoundException;

    protected void fetchTicket() throws Exception {
        log.debug("Fetching ticket with id==" + id);
        if (id == null) {// fatal error!!!
            log.error("Ticket id is missing!!!");            
            ticketModel = null;
            throw new IllegalStateException("Ticket id is missing");
        }
        ticketModel = getTicketModel(id);
        ticketWrapper = null;
    }

    protected void log() {
        log.debug("Ticket id: " + id);
        log.debug("Ticket: " + ticketModel.getTicket());
        log
                .debug("Name servers: "
                        + getTicketWrapper().getCurrentNameserverWrappers());
    }

    /**
     * forwards to the REVISE view.
     *
     * @return always REVISE.
     */
    public String revise() {
        log();
        return REVISE;
    }

    /**
     * forwards to the EDIT view
     *
     * @return always EDIT
     */
    public String edit() {
        log();
        return EDIT;
    }

    /**
     * forwards to the SEARCH view
     *
     * @return always SEARCH
     */
    public String search() {
        log();
        return SEARCH;
    }

    /*
    * JSP helpers
    */

    public List<FailureReason> getNameserverNameFailureReasons() {
        return failureReasonFactory.getFailureReasonsByField(Field.DNS_FAIL);
    }

    public List<FailureReason> getNameserverIpFailureReasons() {
        return failureReasonFactory.getFailureReasonsByField(Field.IP_FAIL);
    }

    public boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public String makeContactInfo(Contact contact){
        return ContactHelper.makeContactInfo(contact);
    }

    public Date getTicketDeletionDate() {
        Date ticketCrDate = getTicketModel().getTicket().getCreationDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ticketCrDate);
        calendar.add(Calendar.DATE, TICKET_EXPIRATION_PERIOD);
        return calendar.getTime();
    }

    @SkipValidation
    public String dnsCheck() {
        try {
            final String domainName = getTicket().getOperation().getDomainNameField().getNewValue();
            for (NameserverWrapper ns : getTicketWrapper().getCurrentNameserverWrappers()) {
                final String name = ns.getName();
                if (name != null && !name.isEmpty()) {
                    dnsCheckService.check(getUser().getUsername(), domainName, name, ns.getIpv4());
                }
            }
            addActionMessage("DNS Check completed with no errors");
            return SUCCESS;
        } catch (DnsCheckProcessingException e) {
            addActionError("Dns check ended with an error: " + StringEscapeUtils.escapeHtml(e.getMessage()));
            LOG.info("DNS check error", e);
            return ERROR;
        } catch (HostNotConfiguredException e) {
            addActionError("DNS Check failed: <pre>" + StringEscapeUtils.escapeHtml(e.getFullOutputMessage()) + "</pre>");
            return ERROR;
        }
    }
}
