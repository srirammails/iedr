package pl.nask.crs.ticket.services;

import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.impl.TransferTicketEmailParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Test
public class TransferTicketEmailParametersTest {
    private Ticket ticket;
    private TransferTicketEmailParameters params;

    private String domainName = "domain.ie";
    private String billingContact = "BILLING";
    private String billingContactOld = "BILLING-OLD";
    private String adminContactOld = "ADMIN-OLD";
    private String adminContactOld1 = "ADMIN-OLD";
    private String adminContact = "ADMIN";
    private String adminContact1 = "ADMIN1";

    private void prepareTicketParameters(boolean useSecondAdminContact) {
        ticket = newTicket(useSecondAdminContact);
        params = new TransferTicketEmailParameters(null, ticket);
    }

    @Test
    public void shouldSupportAllParameterNames() {
        prepareTicketParameters(true);
        List<ParameterName> names = params.getParameterNames();

        for (ParameterName n : names) {
            // should not throw any exceptions!
            params.getParameterValue(n.getName(), false);
        }
    }

    @DataProvider(name = "dataProviderTwoAdmins")
    public Object[][] expectedParamValuesTwoAdmins() {
        return expectedParamValues(true);
    }

    @DataProvider(name = "dataProviderOneAdmin")
    public Object[][] expectedParamValuesOneAdmin() {
        return expectedParamValues(false);
    }

    private Object[][] expectedParamValues(boolean useSecondAdmin) {
        return new Object[][]{
                {ParameterNameEnum.DOMAIN, domainName},
                {ParameterNameEnum.ADMIN_C_EMAIL, contactEmail(adminContact) + (useSecondAdmin ? ("," + contactEmail(adminContact1)) : "")},
                {ParameterNameEnum.REGISTRAR_NAME, billingContactOld},
                {ParameterNameEnum.BILL_C_EMAIL, contactEmail(billingContactOld)},
                {ParameterNameEnum.BILL_C_NAME, billingContactOld},
                {ParameterNameEnum.GAINING_BILL_C_EMAIL, contactEmail(billingContact)},
                {ParameterNameEnum.GAINING_BILL_C_NAME, billingContact},
        };
    }

    @Test(dataProvider = "dataProviderTwoAdmins")
    public void shouldReturnProperParameterValueTwoAdmins(ParameterNameEnum param, String expectedValue) {
        prepareTicketParameters(true);
        assertProper(expectedValue, param);
    }

    @Test(dataProvider = "dataProviderOneAdmin")
    public void shouldReturnProperParameterValueOneAdmin(ParameterNameEnum param, String expectedValue) {
        prepareTicketParameters(false);
        assertProper(expectedValue, param);
    }

    private void assertProper(String value, ParameterNameEnum paramName) {
        AssertJUnit.assertEquals("value for parameter name: " + paramName, value, params.getParameterValue(paramName.getName(), false));
    }

    private Ticket newTicket(boolean useSecondAdminContact) {
        Date anyDate = new Date();
        Date adminStatusChangeDate = anyDate;
        Date techStatusChangeDate = anyDate;
        Date creationDate = anyDate;
        Date changeDate = anyDate;
        Date financialStatusChangeDate = anyDate;
        Date customerStatusChangeDate = anyDate;
        AdminStatus adminStatus = AdminStatusEnum.PASSED;
        TechStatus techStatus = TechStatusEnum.PASSED;
        FinancialStatus financialStatus = FinancialStatusEnum.PASSED;
        CustomerStatus customerStatus = CustomerStatusEnum.NEW;
        String charityCode = "111";
        Period domainPeriod = Period.fromYears(1);
        Contact creator = new Contact("UnImportant");
        boolean hasDocuments = false;
        boolean clikPaid = false;
        Contact checkedOutTo = newContact("CHKOUT");
        DomainOperation operation = newOperation(useSecondAdminContact);
        Ticket t = new Ticket(0L,
                operation,
                adminStatus,
                adminStatusChangeDate,
                techStatus,
                techStatusChangeDate,
                "requesters remark",
                "hostmasters remark",
                creator,
                creationDate,
                changeDate,
                checkedOutTo,
                clikPaid,
                hasDocuments,
                domainPeriod,
                charityCode,
                financialStatus,
                financialStatusChangeDate,
                customerStatus,
                customerStatusChangeDate);
        return t;
    }

    private DomainOperation newOperation(boolean useSecondAdminContact) {
        List<NameserverChange> nameserversField = newNameserversChange();
        List<SimpleDomainFieldChange<Contact>> billingContactFieldChange = newContactChange(billingContactOld, billingContact);
        List<SimpleDomainFieldChange<Contact>> adminContactFieldChange;
        if (useSecondAdminContact) {
            adminContactFieldChange = newContactChange(adminContactOld, adminContact, adminContactOld1, adminContact1);
        } else {
            adminContactFieldChange = newContactChange(adminContactOld, adminContact);
        }
        SimpleDomainFieldChange<Account> resellerAccountField = newAccountChange();
        SimpleDomainFieldChange<String> domainNameField = new SimpleDomainFieldChange<String>(null, domainName);
        Date renewalDate = new Date(); // any date
        DomainOperationType type = DomainOperationType.XFER;
        DomainOperation op = new DomainOperation(
                type,
                renewalDate,
                domainNameField,
                new SimpleDomainFieldChange<String>(null, null),
                new SimpleDomainFieldChange<String>(null, null),
                new SimpleDomainFieldChange<String>(null, null),
                resellerAccountField,
                adminContactFieldChange,
                Collections.singletonList(new SimpleDomainFieldChange<Contact>(null, null)),
                billingContactFieldChange,
                nameserversField);

        return op;
    }

    private List<NameserverChange> newNameserversChange() {
        return Arrays.asList(
                new NameserverChange(new SimpleDomainFieldChange<String>("old.ns.1", "new.ns.1"), new SimpleDomainFieldChange<String>("old.ip.1", "new.ip.1")),
                new NameserverChange(new SimpleDomainFieldChange<String>("old.ns.2", "new.ns.2"), new SimpleDomainFieldChange<String>("old.ip.2", "new.ip.2")));
    }

    private SimpleDomainFieldChange<Account> newAccountChange() {
        Account acc = new Account(1111L, "account name");
        return new SimpleDomainFieldChange<Account>(acc, acc);
    }

    @SuppressWarnings("unchecked")
    private List<SimpleDomainFieldChange<Contact>> newContactChange(String oldC, String newC) {
        return Collections.singletonList(new SimpleDomainFieldChange<Contact>(newContact(oldC), newContact(newC)));
    }

    @SuppressWarnings("unchecked")
    private List<SimpleDomainFieldChange<Contact>> newContactChange(String oldC, String newC, String oldC1, String newC1) {
        return Arrays.asList(
                new SimpleDomainFieldChange<Contact>(newContact(oldC), newContact(newC)),
                new SimpleDomainFieldChange<Contact>(newContact(oldC1), newContact(newC1))
        );
    }

    private Contact newContact(String nh) {
        return new Contact(contactNh(nh), nh, contactEmail(nh), "billingContactCompany", "country", "county");
    }

    private String contactNh(String nh) {
        return nh + "-NH";
    }

    private String contactEmail(String nh) {
        return nh.toLowerCase() + "@somedomain.ie";
    }

}
