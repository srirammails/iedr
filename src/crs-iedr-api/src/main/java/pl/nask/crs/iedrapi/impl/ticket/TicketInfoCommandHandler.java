package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.ContactType;
import ie.domainregistry.ieapi_ticket_1.HolderNameType;
import ie.domainregistry.ieapi_ticket_1.HolderType;
import ie.domainregistry.ieapi_ticket_1.InfDataType;
import ie.domainregistry.ieapi_ticket_1.NsType;
import ie.domainregistry.ieapi_ticket_1.SNameType;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import java.math.BigInteger;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class TicketInfoCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<SNameType> {

	
	@Override
	public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback)
			throws AccessDeniedException, IedrApiException {
		ApiValidator.assertNoError(callback);
		AuthenticatedUser user = auth.getUser();
		Ticket res = viewTicketForDomain(user, command.getName());

        checkTicket(user, res, command.getName());

		DomainOperation op = res.getOperation();
		long resellerId = op.getResellerAccountField().getNewValue().getId();
		InfDataType idt = new InfDataType();
		idt.setAccount(BigInteger.valueOf(resellerId));
		
		idt.setDnsStatus(res.getTechStatus().getDescription());

		HolderType ht = new HolderType();
		ht.setClazz(op.getDomainHolderClassField().getNewValue());
		HolderNameType holderName = new HolderNameType();
		holderName.setCategory(op.getDomainHolderCategoryField().getNewValue());
		holderName.setValue(op.getDomainHolderField().getNewValue());
		ht.setHolderName(holderName);
		ht.setHolderRemarks(res.getRequestersRemark());
		idt.setHolder(ht);
		
		idt.setHostmasterRemarks(res.getHostmastersRemark());
		idt.setHostmasterStatus(res.getAdminStatus().getDescription());
		idt.setName(op.getDomainNameField().getNewValue());
		idt.setRegDate(res.getCreationDate());
		idt.setRenDate(op.getRenewalDate());
		idt.setType(op.getType().getFullName());
		addContacts(idt, op.getAdminContactsField(), ContactAttrType.ADMIN);
		addContacts(idt, op.getTechContactsField(), ContactAttrType.TECH);

		// add nameservers
		for (NameserverChange ns: op.getNameserversField()) {		
			if (!Validator.isEmpty(ns.getName().getNewValue()) || !Validator.isEmpty(ns.getIpAddress().getNewValue())) {
				NsType nst = new NsType();
				if (!Validator.isEmpty(ns.getIpAddress().getNewValue())) 
					nst.getNsAddr().add(ns.getIpAddress().getNewValue());
				nst.setNsName(ns.getName().getNewValue());
				idt.getNs().add(nst );
			}
		}
		
		return ResponseTypeFactory.success(idt);
	}

	private void addContacts(InfDataType idt, List<SimpleDomainFieldChange<Contact>> contacts, ContactAttrType type) {
		for (SimpleDomainFieldChange<Contact> sdf: contacts) {
			if (sdf.getNewValue() != null && !Validator.isEmpty(sdf.getNewValue().getNicHandle())) {
				ContactType e = new ContactType();
				e.setType(type);
				e.setValue(sdf.getNewValue().getNicHandle());
				idt.getContact().add(e );	
			}
		}
	}

}
