package pl.nask.crs.web.ticket.wrappers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.NullIsFalsePredicate;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

/**
 * Wraps the ticket. All sub-wrappers are lazy-initialized
 *
 * @author Artur Gniadzik
 */
public class TicketWrapper {
    private Ticket ticket;
    private final FailureReasonFactory frFactory;
    private final AccountSearchService accountSearchService;
    private final EntityWrapper entityWrapper;

    // values inside the ticket (possibly not saved to the db, only in ticket object in session)
    private List<NameserverWrapper> currentNameserverWrappers;
    // values from the form
    private List<NameserverWrapper> newNameserverWrappers = new LinkedList<NameserverWrapper>();
    private StringDomainFieldChangeWrapper domainHolder;
    private StringDomainFieldChangeWrapper domainName;
    private ContactDomainFieldChangeWrapper adminContact1;
    private ContactDomainFieldChangeWrapper adminContact2;
    private ContactDomainFieldChangeWrapper billingContact;
    private ContactDomainFieldChangeWrapper techContact;
    private AccountDomainFieldChangeWrapper resellerAccount;
    private StringDomainFieldChangeWrapper holderClass;
    private StringDomainFieldChangeWrapper holderCategory;

    public TicketWrapper(Ticket ticket, FailureReasonFactory frFactory,
                         AccountSearchService accountSearchService,
                         EntityClassFactory entityClassFactory) {
        this.ticket = ticket;
        this.frFactory = frFactory;
        this.accountSearchService = accountSearchService;        
        this.entityWrapper = new EntityWrapper(entityClassFactory);
    }

    public StringDomainFieldChangeWrapper getDomainHolder() {
        if (domainHolder == null)
            domainHolder = new StringDomainFieldChangeWrapper(ticket
                    .getOperation().getDomainHolderField(), frFactory,
                    Field.DOMAIN_HOLDER_FAIL, ticket.getOperation().getType());
        return domainHolder;
    }

    public StringDomainFieldChangeWrapper getDomainName() {
        if (domainName == null)
            domainName = new StringDomainFieldChangeWrapper(ticket
                    .getOperation().getDomainNameField(), frFactory,
                    Field.DOMAIN_NAME_FAIL, ticket.getOperation().getType());
        return domainName;
    }

    public ContactDomainFieldChangeWrapper getAdminContact1() {
        if (adminContact1 == null)
            adminContact1 = new ContactDomainFieldChangeWrapper(ticket
                    .getOperation().getAdminContactsField().get(0), frFactory,
                    Field.ADMIN1_CONTACT1_FAIL, ticket.getOperation().getType());
        return adminContact1;
    }

    public ContactDomainFieldChangeWrapper getAdminContact2() {
        if (adminContact2 == null)
            adminContact2 = new ContactDomainFieldChangeWrapper(ticket
                    .getOperation().getAdminContactsField().get(1), frFactory,
                    Field.ADMIN_CONTACT2_FAIL, ticket.getOperation().getType());
        return adminContact2;
    }

    public ContactDomainFieldChangeWrapper getTechContact() {
        if (techContact == null)
            techContact = new ContactDomainFieldChangeWrapper(ticket
                    .getOperation().getTechContactsField().get(0), frFactory,
                    Field.TECH_CONTACT_FAIL, ticket.getOperation().getType());
        return techContact;
    }

    public ContactDomainFieldChangeWrapper getBillingContact() {
        if (billingContact == null)
            billingContact = new ContactDomainFieldChangeWrapper(ticket
                    .getOperation().getBillingContactsField().get(0),
                    frFactory, Field.BILLING_CONTACT_FAIL, ticket
                    .getOperation().getType());
        return billingContact;
    }

    public AccountDomainFieldChangeWrapper getResellerAccount() {
        if (resellerAccount == null)
            resellerAccount = new AccountDomainFieldChangeWrapper(ticket
                    .getOperation().getResellerAccountField(), frFactory,
                    Field.ACCOUNT_NAME_FAIL, accountSearchService, ticket
                    .getOperation().getType());
        return resellerAccount;
    }

    public StringDomainFieldChangeWrapper getDomainHolderClass() {
        if (holderClass == null)
            holderClass = new StringDomainFieldChangeWrapper(ticket
                    .getOperation().getDomainHolderClassField(), frFactory,
                    Field.CLASS_FAIL, ticket.getOperation().getType());
        return holderClass;
    }

    public StringDomainFieldChangeWrapper getDomainHolderCategory() {
        if (holderCategory == null)
            holderCategory = new StringDomainFieldChangeWrapper(ticket
                    .getOperation().getDomainHolderCategoryField(), frFactory,
                    Field.CATEGORY_FAIL, ticket.getOperation().getType());
        return holderCategory;
    }



    

    public long getDomainHolderClassId() {
        return entityWrapper.getClass(getDomainHolderClass().getNewValue()).getId();        
    }

    public void setDomainHolderClassId(long id) {
        getDomainHolderClass().setNewValue(entityWrapper.getClass(id).getName());
    }

    public long getDomainHolderCategoryId() {
        return entityWrapper.getCategory(getDomainHolderClass().getNewValue(), getDomainHolderCategory().getNewValue()).getId();
    }

    public void setDomainHolderCategoryId(long id) {
        getDomainHolderCategory().setNewValue(entityWrapper.getCategory(id).getName());
    }

    public List<NameserverWrapper> getCurrentNameserverWrappers() {
        if (currentNameserverWrappers == null) {
            List<NameserverChange> nameservers = ticket.getOperation()
                    .getNameserversField();
            currentNameserverWrappers = new ArrayList<NameserverWrapper>();
            for (NameserverChange ns : nameservers) {
                currentNameserverWrappers.add(new NameserverWrapper(ns));
            }
        }
        return currentNameserverWrappers;
    }

    public List<NameserverWrapper> getNewNameserverWrappers() {
        return newNameserverWrappers;
    }

    public void updateNameserversWithNewValues() {
        // try to match existing nameservers with new ones:
        CollectionUtils.filter(newNameserverWrappers, PredicateUtils.notNullPredicate());
        List<NameserverChange> newChangeList = new ArrayList<NameserverChange>(newNameserverWrappers.size());
        List<NameserverChange> currentChangeList = ticket.getOperation().getNameserversField();
        for (NameserverWrapper newWrapper : newNameserverWrappers) {
            NameserverChange change = (NameserverChange) CollectionUtils.find(currentChangeList, lookFor(newWrapper.getName()));
            if (change != null) {
                change.getName().setNewValue(newWrapper.getName());
                change.getName().setFailureReason(frFactory.getEntry(newWrapper.getNameFr()));
                change.getIpAddress().setNewValue(newWrapper.getIpv4());
                change.getIpAddress().setFailureReason(frFactory.getEntry(newWrapper.getIpv4Fr()));
            } else {
                change = new NameserverChange(
                    new SimpleDomainFieldChange<String>(null, newWrapper.getName(), frFactory.getEntry(newWrapper.getNameFr())),
                    new SimpleDomainFieldChange<String>(null, newWrapper.getIpv4(), frFactory.getEntry(newWrapper.getIpv4Fr())));
            }
            newChangeList.add(change);
        }
        ticket.getOperation().setNameserversField(newChangeList);
        currentNameserverWrappers = null;
    }

    public void updateNameserversWithNewFailureReasons() {
        CollectionUtils.filter(newNameserverWrappers, PredicateUtils.notNullPredicate());
        List<NameserverChange> currentChangeList = ticket.getOperation().getNameserversField();

        for (int i = 0; i < Math.min(newNameserverWrappers.size(), currentChangeList.size()); i++) {
            final NameserverWrapper newWrapper = newNameserverWrappers.get(i);
            final NameserverChange change = currentChangeList.get(i);
            if (change != null) {
                change.getName().setFailureReason(frFactory.getEntry(newWrapper.getNameFr()));
                change.getIpAddress().setFailureReason(frFactory.getEntry(newWrapper.getIpv4Fr()));
            }
        }
        currentNameserverWrappers = null;
    }

    private Predicate lookFor(final String domainName) {
        return new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((NameserverChange) o).getName().getNewValue().equalsIgnoreCase(domainName);
            }
        };
    }

    public boolean isRegistration() {
        return ticket.getOperation().getType() == DomainOperationType.REG;
    }
    
    public String getNumberOfYears() {
        String res = null;
        try{
            /*
             * return number of days if (requirement from Billy):
             * 
             * T_Type = 'R' AND Admin_Status = 1 AND Tech_Status = 1 and
             * A_Number >= 100
             */
        if (ticket.getOperation().getType() == DomainOperationType.REG && ticket.getAdminStatus().getId() == 1
                && ticket.getTechStatus().getId() == 1
                && ticket.getOperation().getResellerAccountField().getNewValue().getId() > 100) {
            if (ticket.getOperation().getRenewalDate() == null)
                res = "Unknown (renewal date is empty)";
            else {
                    /*
                     * count and return number of years (requirement from
                     * Billy):
                     * 
                     * $Value_To_Display = YEAR(T_Ren_Dt) - YEAR(CURDATE())
                     * 
                     * AND if T_Ren_Dt - CURDATE() < 365 days than value is 1
                     */
                
                Calendar c = Calendar.getInstance();
                int thisYear = c.get(Calendar.YEAR);
                c.setTime(ticket.getOperation().getRenewalDate());
                res = "" + (Math.max(1, c.get(Calendar.YEAR) - thisYear));
            }
                
        }
        } catch (NullPointerException e) {
            //something went wrong!!!
            res = "Unknown (error occurred)";
        }
        return res;    
    }
    
    public String getDomainPeriodInYears() {    	
    	if (ticket.getOperation().getType() == DomainOperationType.REG || ticket.getOperation().getType() == DomainOperationType.XFER) {
    		return "" + ticket.getDomainPeriod().getYears();
    	} else {
    		return null;
    	}
    }
    
    public String getCharityCode() {
    	return ticket.getCharityCode();
    }
}
