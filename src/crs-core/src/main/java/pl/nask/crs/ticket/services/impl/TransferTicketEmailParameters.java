package pl.nask.crs.ticket.services.impl;

import org.apache.commons.lang.StringUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import java.util.ArrayList;
import java.util.List;

public class TransferTicketEmailParameters implements EmailParameters {
    private static List<ParameterName> paramList;

    static {
        paramList = new ArrayList<ParameterName>();
        paramList.add(ParameterNameEnum.DOMAIN);
        paramList.add(ParameterNameEnum.ADMIN_C_NAME);
        paramList.add(ParameterNameEnum.ADMIN_C_EMAIL);
        paramList.add(ParameterNameEnum.BILL_C_NAME);
        paramList.add(ParameterNameEnum.BILL_C_EMAIL);
        paramList.add(ParameterNameEnum.GAINING_BILL_C_NAME);
        paramList.add(ParameterNameEnum.GAINING_BILL_C_EMAIL);
        paramList.add(ParameterNameEnum.REGISTRAR_NAME);
    }

    private Ticket ticket;
    private String username;

    public TransferTicketEmailParameters(String username, Ticket ticket) {
        Validator.assertNotNull(ticket, "ticket");
        this.ticket = ticket;
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return this.username;
    }

    public String getAccountRelatedNicHandle() {
        return this.ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
    }

    public String getDomainName() {
        return this.ticket.getOperation().getDomainNameField().getNewValue();
    }

    public List<ParameterName> getParameterNames() {
        return paramList;
    }

    /**
     * Returns the parameter
     *
     * @param name
     * @return searched value, can be null
     */
    public String getParameterValue(String name, boolean html) {
        Validator.assertNotNull(name, "parameter name");
        ParameterNameEnum ticketName = ParameterNameEnum.forName(name);
        DomainOperation domainOp = ticket.getOperation();
        switch (ticketName) {
            case DOMAIN:
                return domainOp.getDomainNameField().getNewValue();
            case ADMIN_C_EMAIL:
                return getAdminEmail();
            case ADMIN_C_NAME:
                return domainOp.getAdminContactsField().get(0).getNewValue().getName();
            case REGISTRAR_NAME:
            case BILL_C_NAME:
                return getName(domainOp.getBillingContactsField().get(0).getCurrentValue());
            case BILL_C_EMAIL:
                return getEmail(domainOp.getBillingContactsField().get(0).getCurrentValue());
            case GAINING_BILL_C_NAME:
                return getName(domainOp.getBillingContactsField().get(0).getNewValue());
            case GAINING_BILL_C_EMAIL:
                return getEmail(domainOp.getBillingContactsField().get(0).getNewValue());
            default:
                throw new IllegalArgumentException("Unsupported parameter name " + name);
        }
    }

    private String getEmail(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getEmail();
        }
    }

    private String getName(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getName();
        }
    }

    private String getAdminEmail() {
        List<SimpleDomainFieldChange<Contact>> adminList = ticket.getOperation().getAdminContactsField();
        List<String> emails = new ArrayList<String>(adminList.size());
        for (SimpleDomainFieldChange<Contact> admin_field : adminList) {
            final String email = getEmail(admin_field.getNewValue());
            if (email != null) {
                emails.add(email);
            }
        }
        return StringUtils.join(emails, ",");
    }
}
