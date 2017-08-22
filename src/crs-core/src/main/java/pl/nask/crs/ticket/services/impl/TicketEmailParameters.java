package pl.nask.crs.ticket.services.impl;


import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * @author Patrycja Wegrzynowicz, Kasia Fulara
 */
public class TicketEmailParameters implements EmailParameters {

    private static List<ParameterName> paramList = ParameterNameEnum.asList();

    // wrapped object
    private Ticket ticket;
    private int daysRemaining;
    private String username;

    public TicketEmailParameters(String username, Ticket ticket) {
        Validator.assertNotNull(ticket, "ticket");
        this.ticket = ticket;
        this.username = username;
    }

    public TicketEmailParameters(String username, Ticket ticket, int daysRemaining) {
        Validator.assertNotNull(ticket, "ticket");
        this.ticket = ticket;
        this.daysRemaining = daysRemaining;
        this.username = username;
    }

    public String getLoggedInNicHandle()
    {
        return this.username;
    }

    public String getAccountRelatedNicHandle()
    {
        return this.ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
    }

    public String getDomainName()
    {
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
        Contact c = null;
        switch (ticketName) {
            case DOMAIN:
            	return domainOp.getDomainNameField().getNewValue();
            case HOLDER:
            	return domainOp.getDomainHolderField().getNewValue();
            case HOLDER_OLD:
            	return domainOp.getDomainHolderField().getCurrentValue();
            case DOMAIN_CLASS:
            	return domainOp.getDomainHolderClassField().getNewValue();
            case DOMAIN_CLASS_OLD:
            	return domainOp.getDomainHolderClassField().getCurrentValue();
            case DOMAIN_CATEGORY:
            	return domainOp.getDomainHolderCategoryField().getNewValue();
            case DOMAIN_CATEGORY_OLD:
            	return domainOp.getDomainHolderCategoryField().getCurrentValue();
            case REMARK:
                return getRemark();
            case ADMIN_C_EMAIL:
            	 String email1 = getAdminEmail(0);
                 String email2 = getAdminEmail(1);
                 if (email2 == null) {
                 	return email1;
                 } else {
                 	return email1 + "," + email2;
                 }
            case ADMIN_C_NIC:
            	return domainOp.getAdminContactsField().get(0).getNewValue().getNicHandle();
            case ADMIN_C_NAME:
                return domainOp.getAdminContactsField().get(0).getNewValue().getName();
            case ADMIN_C_NIC_OLD:
            	c = domainOp.getAdminContactsField().get(0).getCurrentValue();
            	return c == null ? null : c.getNicHandle();            		
            case TECH_C_NIC:
            	return domainOp.getTechContactsField().get(0).getNewValue().getNicHandle();
            case TECH_C_NIC_OLD:
            	c = domainOp.getTechContactsField().get(0).getCurrentValue();
            	return c == null ? null : c.getNicHandle();
            case CREATOR_C_NAME:
            	return ticket.getCreator().getName();
            case CREATOR_C_EMAIL:
                return ticket.getCreator().getEmail();
            case TECH_C_EMAIL:
            	return getNewEmail(domainOp.getTechContactsField().get(0));
            case REGISTRAR_NAME:
            case BILL_C_NAME:
            case GAINING_BILL_C_NAME:               
                return getNewName(domainOp.getBillingContactsField().get(0));
            case BILL_C_CO_NAME:
            	return domainOp.getBillingContactsField().get(0).getNewValue().getCompanyName();
            case BILL_C_EMAIL:
            case GAINING_BILL_C_EMAIL:
            	return getNewEmail(domainOp.getBillingContactsField().get(0));
            case TECH_C_NAME:
                return getNewName(domainOp.getTechContactsField().get(0));
            case TICKET_ID:
                return String.valueOf(ticket.getId());
            case TICKET_TYPE:
                return domainOp.getType().getFullName().toLowerCase();
            case TICKET_DAYS_REMAINING:
                return String.valueOf(daysRemaining);
            default:
                return null;
        }
    }

    private String getEmail(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getEmail();
        }
    }
    
    private String getNewEmail(SimpleDomainFieldChange<Contact>  contact) {
        if (contact == null) {
            return null;
        } else {
            return getEmail(contact.getNewValue());
        }
    }

    private String getName(Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getName();
        }
    }
    
    private String getNewName(SimpleDomainFieldChange<Contact> contact) {
        if (contact == null) {
            return null;
        } else {
            return getName(contact.getNewValue());
        }
    }


    private String addToRemark(String remark, String text1, String name, String failure) {
        return remark.concat(text1 + " " + name + " [" + failure + "] \n");
    }

    private String getAdminEmail(int number) {
        List<SimpleDomainFieldChange<Contact>> adminList = ticket.getOperation().getAdminContactsField();
        if (adminList.isEmpty() || adminList.size() <= number)
            return null;
        Contact contact = adminList.get(number).getNewValue();
        return getEmail(contact);
    }

    private String getRemark() {
        String remark = "";
        remark = remark.concat(ticket.getHostmastersRemark() + "\n");
        DomainOperation domainOp = ticket.getOperation();

        FailureReason failure = domainOp.getDomainNameField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainNameField().getNewValue() != null) {
                remark = addToRemark(remark, "Domain Name :", domainOp.getDomainNameField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Domain Name :", "", failure.getDescription());
            }
        }

        failure = domainOp.getDomainHolderField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderField().getNewValue() != null) {
                remark = addToRemark(remark, "Domain Holder :", domainOp.getDomainHolderField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Domain Holder :", "", failure.getDescription());
            }
        }

        failure = domainOp.getResellerAccountField().getFailureReason();
        if ((failure != null)) {
            if (domainOp.getResellerAccountField().getNewValue() != null) {
                remark = addToRemark(remark, "Account Name :", domainOp.getResellerAccountField().getNewValue().getName(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Account Name :", "", failure.getDescription());
            }
        }
        List<SimpleDomainFieldChange<Contact>> adminList = domainOp.getAdminContactsField();
        if (!adminList.isEmpty()) {
            for (SimpleDomainFieldChange<Contact> admin : adminList) {
                failure = admin.getFailureReason();
                if (failure != null) {
                    if (admin.getNewValue() != null) {
                        remark = addToRemark(remark, "Admin Contact :", admin.getNewValue().getNicHandle(),
                                failure.getDescription());
                    } else {
                        remark = addToRemark(remark, "Admin Contact :", "", failure.getDescription());
                    }
                }
            }
        }
        adminList = domainOp.getTechContactsField();
        if (!adminList.isEmpty()) {
            SimpleDomainFieldChange<Contact> tech = adminList.get(0);
            failure = tech.getFailureReason();
            if (failure != null) {
                if (tech.getNewValue() != null) {
                    remark = addToRemark(remark, "Tech Contact :", tech.getNewValue().getNicHandle(),
                            failure.getDescription());
                } else {
                    remark = addToRemark(remark, "Tech Contact :", "", failure.getDescription());
                }
            }
        }
        adminList = domainOp.getBillingContactsField();
        if (!adminList.isEmpty()) {
            SimpleDomainFieldChange<Contact> bill = adminList.get(0);
            failure = bill.getFailureReason();
            if (failure != null) {
                if (bill.getNewValue() != null) {
                    remark = addToRemark(remark, "Billing Contact :", bill.getNewValue().getNicHandle(),
                            failure.getDescription());
                } else {
                    remark = addToRemark(remark, "Billing Contact :", "", failure.getDescription());
                }
            }
        }
        List<NameserverChange> nameServers = domainOp.getNameserversField();
        if (!nameServers.isEmpty()) {
            int i = 0;
            for (NameserverChange nameServer : nameServers) {
                failure = nameServer.getName().getFailureReason();
                if (failure != null) {
                    if (nameServer.getName().getNewValue() != null) {
                        remark = addToRemark(remark, "DNS Name " + (i + 1) + " :", nameServer.getName().getNewValue(),
                                failure.getDescription());
                    } else {
                        remark = addToRemark(remark, "DNS ame " + (i + 1) + " :", "", failure.getDescription());
                    }
                }
                failure = nameServer.getIpAddress().getFailureReason();
                if (failure != null) {
                    if (nameServer.getIpAddress().getNewValue() != null) {
                        remark = addToRemark(remark, "DNS IP Addr. " + (i + 1) + " :", nameServer.getIpAddress().getNewValue(),
                                failure.getDescription());
                    } else {
                        remark = addToRemark(remark, "DNS IP Addr. " + (i + 1) + " :", "", failure.getDescription());
                    }
                }
            }
        }

        failure = domainOp.getDomainHolderClassField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderClassField() != null) {
                remark = addToRemark(remark, "Class :", domainOp.getDomainHolderClassField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Class :", "", failure.getDescription());
            }
        }

        failure = domainOp.getDomainHolderCategoryField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderCategoryField() != null) {
                remark = addToRemark(remark, "Category :", domainOp.getDomainHolderCategoryField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Category :", "", failure.getDescription());
            }
        }
        return remark;
    }
}
