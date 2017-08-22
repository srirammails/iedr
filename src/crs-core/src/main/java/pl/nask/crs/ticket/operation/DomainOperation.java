package pl.nask.crs.ticket.operation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;

/**
 * todo: consider how to retrieve "current value" for the historical values
 *
 * @author Patrycja Wegrzynowicz
 */
public class DomainOperation {

    public static enum DomainOperationType {
        REG("R", "Registration"), DEL("D", "Deletion"), MOD("M", "Modification"), XFER("T", "Transfer");

        private String code;

        private String fullName;
        DomainOperationType(String code, String fullName) {
            this.code = code;
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public String getCode() {
            return code;
        }

    }

    /**
     * @see pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket#dataSetType
     */
    private boolean containsDnsData = true;

    /**
     * T_Type not null
     */
    private DomainOperationType type;

    /**
     * D_Name not null, DN_Fail_Cd null
     * <p/>
     * todo: domain name normalized and validated - DomainName class?
     */
    private SimpleDomainFieldChange<String> domainNameField;

    /**
     * D_Holder not null, DH_Fail_Cd null
     */
    private SimpleDomainFieldChange<String> domainHolderField;

    /**
     * T_Class not null, T_Class_Fail_Cd null
     * <p/>
     * todo: change to EntityClass, consider how to treat changes to the dictionary which happen over time
     */
    private SimpleDomainFieldChange<String> domainHolderClassField;

    /**
     * T_Category not null, T_Category_Fail_Cd null
     * <p/>
     * todo: change to EntityCategory, consider how to treat changes to the dictionary which happen over time
     */
    private SimpleDomainFieldChange<String> domainHolderCategoryField;

    /**
     * A_Number not null, AC_Fail_Cd null
     */
    private SimpleDomainFieldChange<Account> resellerAccountField;

    /**
     * Admin_NH2 null, ANH2_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> adminContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    /**
     * Tech_NH not null, TNH_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> techContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    /**
     * Bill_NH not null, BNH_Fail_Cd null
     */
    private List<SimpleDomainFieldChange<Contact>> billingContactsField = new ArrayList<SimpleDomainFieldChange<Contact>>();

    private List<NameserverChange> nameserversField = new ArrayList<NameserverChange>();

    private Date renewalDate;

    public DomainOperation(DomainOperationType type,
                           Date renewalDate, SimpleDomainFieldChange<String> domainNameField,
                           SimpleDomainFieldChange<String> domainHolderField,
                           SimpleDomainFieldChange<String> domainHolderClassFieldChange,
                           SimpleDomainFieldChange<String> domainHolderCategoryField,
                           SimpleDomainFieldChange<Account> resellerAccountField,
                           List<SimpleDomainFieldChange<Contact>> adminContactFieldChange,
                           List<SimpleDomainFieldChange<Contact>> techContactFieldChange,
                           List<SimpleDomainFieldChange<Contact>> billingContactFieldChange,
                           List<NameserverChange> nameserversField,
                           boolean containsDnsData) {
        this(type, renewalDate, domainNameField, domainHolderField, domainHolderClassFieldChange, domainHolderCategoryField, resellerAccountField, adminContactFieldChange, techContactFieldChange, billingContactFieldChange, nameserversField);
        this.containsDnsData = containsDnsData;
    }

    public DomainOperation(DomainOperationType type,
                           Date renewalDate, SimpleDomainFieldChange<String> domainNameField,
                           SimpleDomainFieldChange<String> domainHolderField,
                           SimpleDomainFieldChange<String> domainHolderClassFieldChange,
                           SimpleDomainFieldChange<String> domainHolderCategoryField,
                           SimpleDomainFieldChange<Account> resellerAccountField,
                           List<SimpleDomainFieldChange<Contact>> adminContactFieldChange,
                           List<SimpleDomainFieldChange<Contact>> techContactFieldChange,
                           List<SimpleDomainFieldChange<Contact>> billingContactFieldChange,
                           List<NameserverChange> nameserversField) {
        Validator.assertNotNull(type, "domain change type");
        Validator.assertNotNull(domainNameField, "domain name field");
        Validator.assertNotNull(domainHolderField, "domain holder field");
        Validator.assertNotNull(domainHolderClassFieldChange, "domain holder class field");
        Validator.assertNotNull(domainHolderCategoryField, "domain holder category field");
        Validator.assertNotNull(resellerAccountField, "reseller account field");
        Validator.assertNotNull(adminContactFieldChange, "admin contact field");
        Validator.assertNotNull(techContactFieldChange, "tech contact field");
        Validator.assertNotNull(billingContactFieldChange, "billing contact field");
        Validator.assertNotNull(nameserversField, "nameservers field");
        this.type = type;
        this.domainNameField = domainNameField;
        this.domainHolderField = domainHolderField;
        this.domainHolderClassField = domainHolderClassFieldChange;
        this.domainHolderCategoryField = domainHolderCategoryField;
        this.resellerAccountField = resellerAccountField;
        this.adminContactsField = adminContactFieldChange;
        this.techContactsField = techContactFieldChange;
        this.billingContactsField = billingContactFieldChange;
        this.nameserversField.addAll(nameserversField);
        this.renewalDate = renewalDate != null ? DateUtils.truncate(renewalDate, Calendar.SECOND) : null;
        /*
         * if (type == Type.REG) { Validator.assertNotNull(renewalDate,
         * "renewal date"); }
         */
    }

    

    public Date getRenewalDate() {
        return renewalDate;
    }



    /**
     * Returns the type of the ticket.
     *
     * @return the type of the ticket; never null.
     */
    public DomainOperationType getType() {
        return type;
    }

    /**
     * Returns the the domain name field of the ticket.
     *
     * @return the domain name field of the ticket; never null; modified always false
     */
    public SimpleDomainFieldChange<String> getDomainNameField() {
        return domainNameField;
    }

    /**
     * Returns the domain holder field of the ticket.
     *
     * @return the domain holder field of the ticket; never null
     */
    public SimpleDomainFieldChange<String> getDomainHolderField() {
        return domainHolderField;
    }

    /**
     * Returns the class field representing the class of the domain holder.
     *
     * @return the class field representing the class of the domain holder; nevern null
     */
    public SimpleDomainFieldChange<String> getDomainHolderClassField() {
        return domainHolderClassField;
    }

    public SimpleDomainFieldChange<String> getDomainHolderCategoryField() {
        return domainHolderCategoryField;
    }

    public SimpleDomainFieldChange<Account> getResellerAccountField() {
        return resellerAccountField;
    }

    public List<SimpleDomainFieldChange<Contact>> getAdminContactsField() {
        // todo: return unmodifiable list?
        return adminContactsField;
    }

    public List<SimpleDomainFieldChange<Contact>> getTechContactsField() {
        // todo: return unmodifiable list?
        return techContactsField;
    }

    public List<SimpleDomainFieldChange<Contact>> getBillingContactsField() {
        // todo: return unmodifiable list?
        return billingContactsField;
    }

    public List<NameserverChange> getNameserversField() {
        // todo: return unmodifiable list?
        return nameserversField;
    }

    public void setNameserversField(List<NameserverChange> newNameservers) {
        this.nameserversField = newNameservers;
        containsDnsData = true;
    }

    public void addNameserverChange(NameserverChange newNameserver) {
        nameserversField.add(newNameserver);
    }

    public void removeNameserverChange(String nameserverToRemove) {
        for (int i = 0; i < nameserversField.size(); ++i) {
            NameserverChange chng = nameserversField.get(i);
            final String chngName = chng.getName().getNewValue();
            if (chngName != null && chngName.equalsIgnoreCase(nameserverToRemove)) {
                nameserversField.remove(i);
                break;
            }
        }
    }

    public boolean containsNameserver(String nameserverChange) {
        for (NameserverChange chng : nameserversField) {
            final String chngNameserverName = chng.getName().getNewValue();
            if (chngNameserverName != null && chngNameserverName.equalsIgnoreCase(nameserverChange))
                return true;
        }
        return false;
    }

    public void populateValues(DomainOperation newValues) {
        // todo: do not populate domain field?
        // domainNameField.populateValue(newValues.getDomainNameField());
        domainHolderField.populateValue(newValues.getDomainHolderField());
        domainHolderClassField.populateValue(newValues.getDomainHolderClassField());
        domainHolderCategoryField.populateValue(newValues.getDomainHolderCategoryField());
        resellerAccountField.populateValue(newValues.getResellerAccountField());
        populateValues(adminContactsField, newValues.getAdminContactsField());
        populateValues(techContactsField, newValues.getTechContactsField());
        populateValues(billingContactsField, newValues.getBillingContactsField());
        List<NameserverChange> newNameservers = newValues.getNameserversField();
        if (newNameservers.size() < nameserversField.size()) {
            nameserversField.subList(newNameservers.size(), nameserversField.size()).clear();
        }
        for (int i = 0; i < newNameservers.size(); ++i) {
            if (i >= nameserversField.size()) {
                nameserversField.add(new NameserverChange(
                        new SimpleDomainFieldChange<String>(null, null),
                        new SimpleDomainFieldChange<String>(null, null)
                ));
            }
            NameserverChange ns2 = newNameservers.get(i);
            nameserversField.get(i).populateValue(ns2);
        }
        containsDnsData = true;
    }

    public void populateFailureReasons(DomainOperation newValues) {
        domainNameField.populateFailureReason(newValues.getDomainNameField());
        domainHolderField.populateFailureReason(newValues.getDomainHolderField());
        domainHolderClassField.populateFailureReason(newValues.getDomainHolderClassField());
        domainHolderCategoryField.populateFailureReason(newValues.getDomainHolderCategoryField());
        resellerAccountField.populateFailureReason(newValues.getResellerAccountField());
        populateFailureReasons(adminContactsField, newValues.getAdminContactsField());
        populateFailureReasons(techContactsField, newValues.getTechContactsField());
        populateFailureReasons(billingContactsField, newValues.getBillingContactsField());
        List<NameserverChange> newNameservers = newValues.getNameserversField();
        // set nameserver failure reasons only to existing nameservers
        for (int i = 0; i < Math.min(nameserversField.size(), newNameservers.size()); ++i) {
            NameserverChange ns1 = nameserversField.get(i);
            NameserverChange ns2 = newNameservers.get(i);
            ns1.populateFailureReason(ns2);
        }
    }

    public void clearFailureReasons() {
        domainNameField.setFailureReason(null);
        domainHolderField.setFailureReason(null);
        domainHolderClassField.setFailureReason(null);
        domainHolderCategoryField.setFailureReason(null);
        resellerAccountField.setFailureReason(null);
        clearFailureReasons(adminContactsField);
        clearFailureReasons(techContactsField);
        clearFailureReasons(billingContactsField);
        for (int i = 0; i < nameserversField.size(); ++i) {
            NameserverChange ns1 = nameserversField.get(i);
            ns1.getName().setFailureReason(null);
            ns1.getIpAddress().setFailureReason(null);
        }
    }

    private void populateValues(List<SimpleDomainFieldChange<Contact>> contacts, List<SimpleDomainFieldChange<Contact>> frContacts) {
        for (int i = 0; i < Math.min(contacts.size(), frContacts.size()); ++i) {
            SimpleDomainFieldChange<Contact> contact1 = contacts.get(i);
            SimpleDomainFieldChange<Contact> contact2 = frContacts.get(i);
            contact1.populateValue(contact2);
        }
    }

    private void populateFailureReasons(List<SimpleDomainFieldChange<Contact>> contacts, List<SimpleDomainFieldChange<Contact>> frContacts) {
        for (int i = 0; i < Math.min(contacts.size(), frContacts.size()); ++i) {
            SimpleDomainFieldChange<Contact> contact1 = contacts.get(i);
            SimpleDomainFieldChange<Contact> contact2 = frContacts.get(i);
            contact1.populateFailureReason(contact2);
        }
    }

    private void clearFailureReasons(List<SimpleDomainFieldChange<Contact>> contacts) {
        for (SimpleDomainFieldChange<Contact> contact : contacts) {
            contact.setFailureReason(null);
        }
    }

    public boolean hasFailureReasons() {
        return domainNameField.isFailed() ||
                domainHolderField.isFailed() ||
                domainHolderClassField.isFailed() ||
                domainHolderCategoryField.isFailed() ||
                resellerAccountField.isFailed() ||
                isFailed(adminContactsField) ||
                isFailed(techContactsField) ||
                isFailed(billingContactsField) ||
                isFailed(nameserversField);
    }

    private boolean isFailed(List<? extends DomainFieldChange> list) {
        for (DomainFieldChange change : list) {
            if (change.isFailed()) return true;
        }
        return false;
    }

    public List<Contact> getAllContactList(){
        ArrayList<Contact> result = new ArrayList<Contact>();
        Contact contact;
        if ((contact = this.getAdminContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getAdminContactsField().get(1).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getTechContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        if ((contact = this.getBillingContactsField().get(0).getNewValue()) != null)
            result.add(contact);
        return result;
    }

}
