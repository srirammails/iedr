package pl.nask.crs.domains.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ibatis.converters.ContactConverter;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class DomainConverter extends AbstractConverter<InternalDomain, Domain> {

    private ContactConverter contactConverter = new ContactConverter();
    private ContactConverter adminConverter = new ContactConverter(InternalContact.ADMIN);
    private ContactConverter techConverter = new ContactConverter(InternalContact.TECH);
    private ContactConverter billingConverter = new ContactConverter(InternalContact.BILLING);
    private ContactConverter creatorConverter = new ContactConverter(InternalContact.CREATOR);
    private NameserverConverter nameserverConverter = new NameserverConverter();

    public DomainConverter() {
    }

    protected Domain _to(InternalDomain src) {
        List<Contact> adminContacts = new ArrayList<Contact>();
        List<Contact> techContacts = new ArrayList<Contact>();
        List<Contact> billingContacts = new ArrayList<Contact>();
        Contact creator = contactConverter.to(src.getCreator());

        splitContacts(src.getContacts(), adminContacts, techContacts, billingContacts);
        
        List<Nameserver> nameservers = nameserverConverter.to(src.getNameservers());

        Domain d =  new Domain(
                src.getName(),
                src.getHolder(),
                src.getHolderClass(),
                src.getHolderCategory(),
                creator,
                createAccount(src.getResellerAccountId(), src.getResellerAccountName()),
                src.getRegistrationDate(),
                src.getRenewDate(),
                src.getRemark(),
                src.getChangeDate(),
                src.isClikPaid(),
                techContacts,
                billingContacts,
                adminContacts,
                nameservers,
                src.getDsmState(),
                src.isZonePublished(),
                src.isPendingCCReservations(),
                src.isPendingADPReservations()
        );
        d.setGiboRetryTimeout(src.getGiboRetryTimeout());
        d.setSuspensionDate(src.getSuspensionDate());
        d.setDeletionDate(src.getDeletionDate());
        d.setTransferDate(src.getTransferDate());
        d.setAuthCode(src.getAuthCode());
        d.setAuthCodeExpirationDate(src.getAuthCodeExpirationDate());
        d.setAuthCodePortalCount(src.getAuthCodePortalCount());
        return d;
    }

    private void splitContacts(List<InternalContact> src, List<Contact> adminContacts,
			List<Contact> techContacts, List<Contact> billingContacts) {
    	for (InternalContact srcContact : src) {
            Contact contact = contactConverter.to(srcContact);
            if (srcContact.isAdmin()) adminContacts.add(contact);
            else if (srcContact.isBilling()) billingContacts.add(contact);
            else if (srcContact.isTech()) techContacts.add(contact);
        }
	}

	protected InternalDomain _from(Domain domain) {
        InternalDomain ret = new InternalDomain();
        ret.setName(domain.getName());
        ret.setHolder(domain.getHolder());
        ret.setHolderCategory(domain.getHolderCategory());
        ret.setHolderClass(domain.getHolderClass());
        ret.setResellerAccountId(domain.getResellerAccount().getId());
        ret.setResellerAccountName(domain.getResellerAccount().getName());
        ret.setRegistrationDate(domain.getRegistrationDate());
        ret.setRenewDate(domain.getRenewDate());
        ret.setRemark(domain.getRemark());
        ret.setAuthCode(domain.getAuthCode());
        ret.setAuthCodeExpirationDate(domain.getAuthCodeExpirationDate());
        ret.setAuthCodePortalCount(domain.getAuthCodePortalCount());
        ret.setChangeDate(domain.getChangeDate());
        ret.setClikPaid(domain.isClikPaid());
        ret.setDateRoll(domain.getDateRoll());
        ret.setDsmState(domain.getDsmState());
        List<InternalContact> contacts = new ArrayList<InternalContact>();
        contacts.addAll(adminConverter.from(domain.getAdminContacts()));
        contacts.addAll(billingConverter.from(domain.getBillingContacts()));
        contacts.addAll(techConverter.from(domain.getTechContacts()));
        InternalContact creator = creatorConverter.from(domain.getCreator());
        if (creator != null) {
            creator.setType("C");
            contacts.add(creator);
        }
        ret.setContacts(contacts);
        List<InternalNameserver> nameservers = new ArrayList<InternalNameserver>();
        nameservers.addAll(nameserverConverter.from(domain.getNameservers()));
        ret.setNameservers(nameservers);
        ret.setGiboRetryTimeout(domain.getGiboRetryTimeout());
        ret.setSuspensionDate(domain.getSuspensionDate());
        ret.setDeletionDate(domain.getDeletionDate());
        ret.setTransferDate(domain.getTransferDate());
        return ret;
    }

    protected Account createAccount(Long id, String name) {
        return id == null ? null : new Account(id, name);
    }

}
