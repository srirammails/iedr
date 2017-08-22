package pl.nask.crs.web.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.app.domains.wrappers.DomainWrapper;
import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Kasia Fulara
 */
public class DomainEditAction extends AuthenticatedUserAwareAction {

    private final static Logger log = Logger.getLogger(DomainEditAction.class);
    private DomainAppService domainAppService;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private NicHandleSearchService nicHandleSearchService;
    private DomainWrapper domainWrapper;
    private String domainName;
    private EntityClassFactory entityClassFactory;
    private String previousAction = "domains-search";

    private static final String EXECUTE = "execute";
    private static final String SAVE = "save";

    private Dictionary<String, BillingStatus> billingStatusDictionary;

    private final static List<BillingStatus> BILLING_STATUSES_NULL = new ArrayList<BillingStatus>(0);
    private List<BillingStatus> billingStatuses = BILLING_STATUSES_NULL;
    private List<Contact> oldAdminContacts;
    private List<Contact> oldTechContacts;

    public DomainEditAction(DomainAppService domainAppService, DnsCheckService dnsCheckService, DnsNotificationService dnsNotificationService,
                            NicHandleSearchService nicHandleSearchService,
                            Dictionary<String, BillingStatus> billingStatusDictionary, EntityClassFactory entityClassFactory) {
        Validator.assertNotNull(domainAppService, "domain application service");
        Validator.assertNotNull(dnsCheckService, "dns check service");
        Validator.assertNotNull(dnsNotificationService, "dns notification service");
        this.domainAppService = domainAppService;
        this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.entityClassFactory = entityClassFactory;
        this.billingStatusDictionary = billingStatusDictionary;

    }

    public List<BillingStatus> getBillingStatuses() {
        if (billingStatusDictionary == null)
            log.error("adminStatusFactory is null!");
        if (billingStatuses == BILLING_STATUSES_NULL) {
            try {
                billingStatuses = billingStatusDictionary.getEntries();
            } catch (Exception e) {
                log.error("Error fetching admin statuses", e);
                addActionError("Error fetching admin statuses");
            }
        }

        return billingStatuses;
    }

    public DomainAppService getDomainAppService() {
        return domainAppService;
    }

    public void setDomainAppService(DomainAppService domainAppService) {
        Validator.assertNotNull(domainAppService, "domain application service");
        this.domainAppService = domainAppService;
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

    public List<EnchancedEntityClass> getDomainClasses() {
        return entityClassFactory.getEntries();
    }

    public List<Contact> getOldAdminContacts() {
        return oldAdminContacts;
    }

    public void setOldAdminContacts(List<Contact> oldAdminContacts) {
        this.oldAdminContacts = oldAdminContacts;
    }

    public List<Contact> getOldTechContacts() {
        return oldTechContacts;
    }

    public void setOldTechContacts(List<Contact> oldTechContacts) {
        this.oldTechContacts = oldTechContacts;
    }

    public String input() throws Exception {
        ExtendedDomainInfo domainInfo = domainAppService.edit(getUser(), domainName);
        oldAdminContacts = new ArrayList<Contact>(domainInfo.getDomain().getAdminContacts());
        oldTechContacts = new ArrayList<Contact>(domainInfo.getDomain().getTechContacts());
        domainWrapper = new DomainWrapper(domainInfo, entityClassFactory);
        return EXECUTE;
    }

    public String save() throws Exception {
        try {
            Domain domain = domainWrapper.getDomain();
            getDomainAppService().save(getUser(), domain);
            if (isAdminOrTechChanged()) {
                dnsCheck();
            }
            return SAVE;
        } catch (NicHandleNotActiveException ex){
            addActionError("Nic handle " + ex.getNicHandleId() + " has not Active status.");
            return ERROR;            
        }
    }

    private boolean isAdminOrTechChanged() {
        String oldTechContact = oldTechContacts.get(0).getNicHandle();
        String newTechContact = domainWrapper.getTechContact();
        if(!oldTechContact.equalsIgnoreCase(newTechContact)) {
            return true;
        }

        String oldAdminContact1 = oldAdminContacts.get(0).getNicHandle();
        String newAdminContact1 = domainWrapper.getAdminContact1();
        if (!oldAdminContact1.equalsIgnoreCase(newAdminContact1)) {
            return true;
        }

        String oldAdminContact2 = oldAdminContacts.size() > 1 ? oldAdminContacts.get(1).getNicHandle() : null;
        String newAdminContact2 = domainWrapper.getAdminContact2();
        if (Validator.isEmpty(oldAdminContact2) && !Validator.isEmpty(newAdminContact2)) {
            return true;
        }
        if (oldAdminContact2 != null && !oldAdminContact2.equalsIgnoreCase(newAdminContact2)) {
            return true;
        }
        return false;
    }

    public void dnsCheck() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        Set<String> failedNameservers = new HashSet<String>();
        for (NameserverStub ns: domainWrapper.getDnsWrapper().getCurrentNameservers()) {
            failedNameservers.add(ns.getName());
        }
        try {
            for (NameserverStub ns: domainWrapper.getDnsWrapper().getCurrentNameservers()) {
                dnsCheckService.check(getUser().getUsername(), domainName, ns.getName(), ns.getIp());
                failedNameservers.remove(ns.getName());
            }
        } catch (DnsCheckProcessingException e) {
            log.error("Error performing DNS check", e);
        } catch (HostNotConfiguredException e) {
            createNotification(e);
        } finally {
            dnsNotificationService.removeAllNotificationsExceptFor(domainName, failedNameservers);
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


    public String makeContactInfo(Contact contact){
        return ContactHelper.makeContactInfo(contact);
    }

}
