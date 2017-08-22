package pl.nask.crs.web.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.wrappers.DomainWrapper;
import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DomainStatus;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NewDomainStatus;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author Kasia Fulara
 * @author Piotr Tkaczyk
 */
public class DomainViewAction extends HistoricalDomainAction {

    private DomainAppService domainAppService;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private NicHandleSearchService nicHandleSearchService;
    private DomainWrapper domainWrapper;
    private String domainName;
        
    String previousAction = "domains-search";
    private List<NewDomainStatus> domainStatuses;
    private static List<NewDomainStatus> giboStatuses = new ArrayList<NewDomainStatus>();;
    static {
    	giboStatuses.add(NewDomainStatus.Active);
		giboStatuses.add(NewDomainStatus.Deleted);
    }

    public DomainViewAction(DomainAppService domainAppService, HistoricalDomainService historicalDomainService,
                            AccountSearchService accountSearchService, DnsCheckService dnsCheckService,
                            DnsNotificationService dnsNotificationService, NicHandleSearchService nicHandleSearchService) {
        super(domainAppService, historicalDomainService, accountSearchService);
        this.domainAppService = domainAppService;
		this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
        this.nicHandleSearchService = nicHandleSearchService;
    }

    public List<NewDomainStatus> getDomainStatuses() {
        if (domainStatuses == null) {
            domainStatuses = new ArrayList<NewDomainStatus>();
            if (domainAppService.isEventValid(getUser(), domainName, DsmEventName.EnterVoluntaryNRP)) {
            	domainStatuses.add(NewDomainStatus.Deleted);
            } else if (domainAppService.isEventValid(getUser(), domainName, DsmEventName.RemoveFromVoluntaryNRP)) {
            	domainStatuses.add(NewDomainStatus.Reactivate);
            }
            
            // check, if triplePass is needed for a domain
            NRPStatus currentStatus = domainWrapper.getDomain().getDsmState().getNRPStatus();
            switch (currentStatus) {
			case InvoluntaryMailedPaymentPending:
			case InvoluntarySuspendedPaymentPending:
			case PostTransactionAudit:
			case TransactionFailed:
				domainStatuses.add(NewDomainStatus.Active);
            }
        }
        return domainStatuses;
    }

    public DomainWrapper getDomainWrapper() {
        return domainWrapper;
    }

    public void setDomainWrapper(DomainWrapper domainWrapper) {
        this.domainWrapper = domainWrapper;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    /**
     * needed in domain-view.jsp to know where to go back from contact view
     *
     * @return false (this is not history domain)
     */
    public boolean isHistory() {
        return false;
    }

    public String execute() throws Exception {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        setHistoricalDomainName(domainName);
        search();
        return SUCCESS;
    }

    public String makeContactInfo(Contact contact){
        return ContactHelper.makeContactInfo(contact);
    }

    public boolean isGIBOWaitingForApproval() {
    	Boolean wipo = domainWrapper.getDomain().getDsmState().getWipoDispute();
    	NRPStatus nrp = domainWrapper.getDomain().getDsmState().getNRPStatus();
    	return (nrp == NRPStatus.PostTransactionAudit || nrp == NRPStatus.TransactionFailed) && wipo != null && !wipo; 
    }       
    
    public List<NewDomainStatus> getGiboStatuses() {
    	return giboStatuses;
    }
    
    public List<DomainHolderType> getHolderTypes() {
    	List<DomainHolderType> types = new ArrayList<DomainHolderType>();
    	for (DomainHolderType t: DomainHolderType.values()) {
    		if (t != DomainHolderType.NA && t != domainWrapper.getDomain().getDsmState().getDomainHolderType()) {
    			types.add(t);
    		}
    	}
    	return types;
    }

    public String dnsCheck() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        Set<String> failedNameservers = new HashSet<String>();
        for (NameserverStub ns : domainWrapper.getDnsWrapper().getCurrentNameservers()) {
            failedNameservers.add(ns.getName());
        }
        try {
            for (NameserverStub ns : domainWrapper.getDnsWrapper().getCurrentNameservers()) {
                dnsCheckService.check(getUser().getUsername(), domainName, ns.getName(), ns.getIp());
            }
            addActionMessage("DNS Check compete: Passed");
            return SUCCESS;
        } catch (DnsCheckProcessingException e) {
            addActionError("Error performing DNS check: " + StringEscapeUtils.escapeHtml(e.getMessage()));
            log.error("Error performing DNS check", e);
            return ERROR;
        } catch (HostNotConfiguredException e) {
            createNotification(e);
            addActionError("DNS Check complete: failed <pre>" + StringEscapeUtils.escapeHtml(e.getFullOutputMessage(false)) + "</pre>");
            return SUCCESS;
        } finally {
            dnsNotificationService.removeAllNotificationsExceptFor(domainName, failedNameservers);
        }
    }
    
    public boolean isWipo() {
    	Boolean wipo = domainWrapper.getDomain().getDsmState().getWipoDispute();
    	if (wipo == null) {
    		return false;
    	} else {
    		return wipo;
    	}
    }

    private void createNotification(HostNotConfiguredException e) {
        try {
            NicHandle techNh = nicHandleSearchService.getNicHandle(domainWrapper.getTechContact());
            DnsNotification notification = new DnsNotification(techNh.getNicHandleId(), techNh.getEmail(), domainWrapper.getName(), e.getNsName(), DateFormatUtils.format(new Date(), "HH:mm"), e.getFullOutputMessage(true));
            dnsNotificationService.createNotification(notification);
        } catch (NicHandleNotFoundException e1) {
            throw new IllegalStateException("tech nicHandle not found", e1);
        }
    }

    public String sendAuthCode() {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        try {
            domainAppService.sendAuthCodeByEmail(getUser(), domainName);
            addActionMessage("Email sent successfully");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Sending email failure: " + e.getMessage());
            return ERROR;
        }
    }

}
