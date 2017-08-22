package pl.nask.crs.app.utils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import java.util.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class TicketConverter {

    public static List<NameserverChange> asNameserverChangeList(List<Nameserver> nameservers) {
        List<NameserverChange> nsChangeList = new ArrayList<NameserverChange>();
        for (Nameserver ns : nameservers) {
            nsChangeList.add(new NameserverChange(new SimpleDomainFieldChange<String>(null, ns.getName()), new SimpleDomainFieldChange<String>(null, ns.getIpAddress() == null ? null : ns.getIpAddress().getAddress())));
        }
        return nsChangeList;
    }

    public static List<SimpleDomainFieldChange<Contact>> asDomainFieldChangeContactList(Collection<String> nicHandles) {
        List<SimpleDomainFieldChange<Contact>> list = new ArrayList<SimpleDomainFieldChange<Contact>>();
        if (nicHandles != null) {
            for (String nh : nicHandles) {
                if (!Validator.isEmpty(nh))
                    list.add(new SimpleDomainFieldChange<Contact>(null, new Contact(nh)));
            }
        }
        return list;
    }

    public static List<SimpleDomainFieldChange<Contact>> asDomainFieldChangeContactList(String... nicHandles) {
        return asDomainFieldChangeContactList(Arrays.asList(nicHandles));
    }


    public static DomainOperation asDomainOperation(DomainOperation.DomainOperationType type, Date renewalDate,
                                                    String domainName, String domainHolder, String domainHolderClass,
                                                    String domainHolderCategory, long resellerAccountId, String resellerAccountName,
                                                    List<SimpleDomainFieldChange<Contact>> adminList,
                                                    List<SimpleDomainFieldChange<Contact>> techList,
                                                    List<SimpleDomainFieldChange<Contact>> billingList,
                                                    List<NameserverChange> nsList) {

        return new DomainOperation(
                type, renewalDate,
                new SimpleDomainFieldChange<String>(null, domainName),
                new SimpleDomainFieldChange<String>(null, domainHolder),
                new SimpleDomainFieldChange<String>(null, domainHolderClass),
                new SimpleDomainFieldChange<String>(null, domainHolderCategory),
                new SimpleDomainFieldChange<Account>(null, new Account(resellerAccountId, resellerAccountName)),
                adminList, techList, billingList, nsList);
    }
}
