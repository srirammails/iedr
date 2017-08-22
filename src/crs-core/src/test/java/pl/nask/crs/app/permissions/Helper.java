package pl.nask.crs.app.permissions;

import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.utils.TicketConverter;
import pl.nask.crs.commons.Period;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class Helper {

    public static TicketRequest prepareCreateRequest(final String domainName, final String billingNH, final long accountId) {
        return new TicketRequest() {
            @Override
            public String getDomainName() {
                return domainName;
            }

            @Override
            public String getDomainHolder() {
                return "domain holder";
            }

            @Override
            public String getDomainHolderClass() {
                return "Natural Person";
            }

            @Override
            public String getDomainHolderCategory() {
                return "Personal Name";
            }

            @Override
            public String getAdminContact1NicHandle() {
                return billingNH;
            }

            @Override
            public String getAdminContact2NicHandle() {
                return billingNH;
            }

            @Override
            public String getTechContactNicHandle() {
                return billingNH;
            }

            @Override
            public List<Nameserver> getNameservers() {
                return Arrays.asList(
                    new Nameserver("dns1.com"),
                    new Nameserver("dns2.com")
                );
            }

            @Override
            public Nameserver getNameserver(int order) {
                return getNameservers().get(order);
            }

            @Override
            public String getRequestersRemark() {
                return "domain creation request";
            }

            @Override
            public String getCharityCode() {
                return null;
            }

            @Override
            public Period getRegPeriod() {
                return Period.fromYears(1);
            }

            @Override
            public boolean isCharity() {
                return false;
            }

            @Override
            public String getAuthCode() {
                return null;
            }

            @Override
            public Ticket toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType) {
                List<SimpleDomainFieldChange<Contact>> adminList = TicketConverter.asDomainFieldChangeContactList(getAdminContact1NicHandle(), getAdminContact2NicHandle());
                List<SimpleDomainFieldChange<Contact>> techList = TicketConverter.asDomainFieldChangeContactList(getTechContactNicHandle());
                List<SimpleDomainFieldChange<Contact>> billingList = TicketConverter.asDomainFieldChangeContactList(billingNH);
                List<NameserverChange> nsList = TicketConverter.asNameserverChangeList(getNameservers());

                Date crDate = new Date();
                DomainOperation domainOp = TicketConverter.asDomainOperation(DomainOperation.DomainOperationType.REG, crDate, getDomainName(), getDomainHolder(), getDomainHolderClass(), getDomainHolderCategory(), accountId, null,
                        adminList, techList, billingList, nsList);

                TechStatus tStatus = TechStatusEnum.NEW;

                AdminStatus aStatus = AdminStatusEnum.NEW;

                return new Ticket(domainOp, aStatus, crDate, tStatus, crDate,getRequestersRemark(), "", new Contact(creatorNh),
                        crDate, crDate, null, false, false, getRegPeriod(), getCharityCode(), FinancialStatusEnum.NEW, crDate);
            }

            @Override
            public List<String> getAdminContacts() {
                return null;
            }
        };
    }
}
