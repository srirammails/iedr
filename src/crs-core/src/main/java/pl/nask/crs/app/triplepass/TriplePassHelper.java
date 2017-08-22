package pl.nask.crs.app.triplepass;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.AdminStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class TriplePassHelper {

    private TriplePassHelper() {}

    public static boolean needsFinancialCheck(Ticket t) {
        return !isCanceled(t) && isAdminPassed(t) && isTechPassed(t) && !isFinancialPassed(t) && !isModificationTicket(t);
    }

    public static boolean needsDnsCheck(Ticket t) {
        return !isCanceled(t) && isAdminPassed(t) && !isTechPassed(t);
    }

    public static boolean isRegistrationTicket(Ticket t) {
        return DomainOperation.DomainOperationType.REG == t.getOperation().getType();
    }

    public static boolean isTransferTicket(Ticket t) {
        return DomainOperation.DomainOperationType.XFER == t.getOperation().getType();
    }

    public static boolean isModificationTicket(Ticket t) {
        return DomainOperation.DomainOperationType.MOD == t.getOperation().getType();
    }

    public static boolean isFullPassed(Ticket t) {
        if (isModificationTicket(t)) {
            return isAdminPassed(t) && isTechPassed(t);
        } else {
            return isAdminPassed(t) && isTechPassed(t) && isFinancialPassed(t);
        }
    }

    public static boolean isFinancialPassed(Ticket t) {
        return t.getFinancialStatus().getId() == FinancialStatus.PASSED;
    }

    public static boolean isTechPassed(Ticket t) {
        return t.getTechStatus().getId() == TechStatus.PASSED;
    }

    public static boolean isAdminPassed(Ticket t) {
        return  t.getAdminStatus().getId() == AdminStatus.PASSED;
    }
    
    public static boolean isCanceled(Ticket t) {
    	return t.getCustomerStatus().getId() == CustomerStatusEnum.CANCELLED.getId();
    }

    public static boolean isAdminCancelled(Ticket t) {
        return t.getAdminStatus().getId() == AdminStatusEnum.CANCELLED.getId();
    }

    public static List<Nameserver> convertNameservers(List<NameserverChange> nameserversField) {
        List<Nameserver> list = new ArrayList<Nameserver>();
        int counter = 0;
        for (NameserverChange chng: nameserversField) {
            if (chng != null && !Validator.isEmpty(chng.getName().getNewValue())) {
                Nameserver ns = new Nameserver(
                        chng.getName().getNewValue(),
                        IPAddress.instanceFor(chng.getIpAddress().getNewValue()),
                        counter);
                list.add(ns);
                counter++;
            }
        }
        return list;
    }

    public static List<String> convertContactsToStringList(List<SimpleDomainFieldChange<Contact>> contacts) {
        List<String> res = new ArrayList<String>();
        for (SimpleDomainFieldChange<Contact> c: contacts) {
            if (c.getNewValue() != null) {
                res.add(c.getNewValue().getNicHandle());
            }
        }
        return res;
    }

    public static List<Contact> convertContactsToContactList(List<SimpleDomainFieldChange<Contact>> contacts) {
        List<Contact> res = new ArrayList<Contact>();
        for (SimpleDomainFieldChange<Contact> c: contacts) {
            if (c.getNewValue() != null) {
                res.add(c.getNewValue());
            }
        }
        return res;
    }

}
