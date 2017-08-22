package pl.nask.crs.iedrapi.impl.ticket;

import static pl.nask.crs.iedrapi.impl.CommandValidationHelper.isEmpty;
import static pl.nask.crs.iedrapi.impl.ticket.TicketValidationHelper.checkTicketNss;
import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.AddType;
import ie.domainregistry.ieapi_ticket_1.ChgType;
import ie.domainregistry.ieapi_ticket_1.ContactType;
import ie.domainregistry.ieapi_ticket_1.HolderType;
import ie.domainregistry.ieapi_ticket_1.ModifyType;
import ie.domainregistry.ieapi_ticket_1.NsNameType;
import ie.domainregistry.ieapi_ticket_1.NsType;
import ie.domainregistry.ieapi_ticket_1.RemType;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.CommandUseError;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectStatusProhibitsOperationException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.RequiredParameterMissingException;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

/**
 * Handler for ticket:modify iedr-api command.
 * 
 * This class is not thread safe!
 * 
 * @author Artur Gniadzik
 *
 */
public class TicketModifyCommandHandler extends AbstractTicketCommandHandler implements
		APICommandHandler<ModifyType> {

    private ApplicationConfig appConfig;

    private int adminCount;
    private int techCount;
    private static final int MIN_CONTACTS_COUNT = 1;
    private static final int MAX_ADMIN_CONTACTS_COUNT = 2;
    private static final int MAX_TECH_CONTACTS_COUNT = 1;

    public TicketModifyCommandHandler(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
	public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback)
			throws AccessDeniedException, IedrApiException {
		String domainName = command.getName();				
		if (Validator.isEmpty(domainName))
			throw new RequiredParameterMissingException(ReasonCode.TICKET_NAME_IS_MANDATORY_FIELD);
		try {
			ApiValidator.assertNoError(callback);
			/* 
			 * FIXME: since the ticket HAS TO be checked-out before it can be modified,
			 * we have to perform checkOut, modify and checkIn. Every operation updates ticket history, 
			 * so we have to wait 1 second between every operation (operation date is part of the key)
			 * 
			 * Also, the requirement not to allow deletion tickets to be modified is 
			 * currently present only in the IEDR-API (no such assumptions in the CRS-API are made)
			 */ 
			AuthenticatedUserVO user = auth.getUser();
			Ticket ticket = viewTicketForDomain(user, domainName);	

            checkTicket(user, ticket, domainName);

            if (ticket.getOperation().getType() == DomainOperationType.DEL) {
                throw new ObjectStatusProhibitsOperationException(ReasonCode.DELETION_TICKET_IS_NOT_ALLOWED_TO_MODIFY, new Value("name", TICKET_NAMESPACE, command.getName()));
            }


			DomainOperation operation = ticket.getOperation();
			String holderRemarks = null;
            calculateNsAndContactsCount(operation);
			AdminStatusEnum newStatus = AdminStatusEnum.RENEW;
			// change data
			if (command.getChg() != null) {
				ChgType chg = command.getChg();
				HolderType holder = chg.getHolder();
				if (holder != null) {
					
					if (holder.getClazz() != null) {
						operation.getDomainHolderClassField().setNewValue(holder.getClazz());
					}
					if (!Validator.isEmpty(holder.getHolderRemarks())) {
						holderRemarks = holder.getHolderRemarks();
					}
					if (holder.getHolderName().getCategory() != null) {
						operation.getDomainHolderCategoryField().setNewValue(holder.getHolderName().getCategory());
					}
					if (holder.getHolderName().getValue() != null) {
						operation.getDomainHolderField().setNewValue(holder.getHolderName().getValue());
					}
				}
				if (chg.getStatus() != null) {
					switch (chg.getStatus()) {
					case DOCSENT: newStatus = AdminStatusEnum.DOCUMENTS_SUBMITTED; break;
					default:
						throw new ParamValuePolicyErrorException("Unknown admin status");
					}
				}
			}

			// remove data
			if (command.getRem() != null) {
				RemType rem = command.getRem();
				for (ContactType c: rem.getContact()) {
					switch (c.getType()) {
						case ADMIN: 
							removeContact(operation.getAdminContactsField(), c);
							break;
						case TECH:
							removeContact(operation.getTechContactsField(), c);
							break;
						default:
							throw new ParamValuePolicyErrorException("Unknown Contact type: " + c.getType());
					}
				}

				for (NsNameType ns: rem.getNs()) {
					removeNs(operation, ns);
				}
			}

			// add data
			if (command.getAdd() != null) {
				AddType add = command.getAdd();
				for (ContactType c: add.getContact()) {
					switch (c.getType()) {
					case ADMIN:
						addContact(operation.getAdminContactsField(), c, 1);
						break;
					case TECH:
						addContact(operation.getTechContactsField(), c, 2);
						break;
					default:
						throw new ParamValuePolicyErrorException("Unknown Contact type: " + c.getType());
					}
				}
				
				for (NsType ns: add.getNs()) {
					addNs(operation, ns);
				}
			}

            checkTicketNss(command.getName(), toNsTypeList(operation.getNameserversField()), appConfig.getNameserverMinCount(), appConfig.getNameserverMaxCount(), TICKET_NAMESPACE);
            checkTicketContacts(user, getAccountId(user), toStringList(operation.getTechContactsField()), MIN_CONTACTS_COUNT, MAX_TECH_CONTACTS_COUNT, ReasonCode.TOO_FEW_TECH_CONTACTS, ReasonCode.TOO_MANY_TECH_CONTACTS, this.techCount);
            checkTicketContacts(user, getAccountId(user), toStringList(operation.getAdminContactsField()), MIN_CONTACTS_COUNT, MAX_ADMIN_CONTACTS_COUNT, ReasonCode.TOO_FEW_ADMIN_CONTACTS, ReasonCode.TOO_MANY_ADMIN_CONTACTS, this.adminCount);

            if (holderRemarks == null) {
            	getTicketAppService().update(user, ticket.getId(), operation , "Modified with IEDR-API", ticket.isClikPaid(), true);
            } else {
            	getTicketAppService().update(user, ticket.getId(), operation , holderRemarks, "Modified with IEDR-API", ticket.isClikPaid(), true);
            }
            if (newStatus == AdminStatusEnum.DOCUMENTS_SUBMITTED) {
            	getTicketAppService().adminDocSent(user, ticket.getId());
            } else {
            	getTicketAppService().adminRenew(user, ticket.getId());
            }
            
		} catch (TicketNotFoundException e) {
			throw new ParamValuePolicyErrorException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST, new Value("name", TICKET_NAMESPACE, command.getName()));
		} catch (TicketCheckedOutToOtherException e) {
			// shall never happen an the ticket is being checked-out by ourself
			throw new ParamValuePolicyErrorException(ReasonCode.TICKET_IN_USE);
		} catch (TicketNotCheckedOutException e) {
			// will never happen
			throw new CommandFailed(e);
		} catch (EmptyRemarkException e) {
			// will never happen
			throw new CommandFailed(e);
		} catch (ContactNotActiveException e) {
            throw new CommandFailed(e);
        } catch (TicketEditFlagException e) {
            throw new CommandFailed(e);
        } catch (ContactNotFoundException e) {
        	// should not happen since validation is performed before update
        	throw new CommandFailed(e);
		} catch (TicketEmailException e) {
            throw new CommandFailed(e);
        } catch (TicketNameserverException e) {
            // should not happen, ticket nameserver counts are checked before update call
            throw new CommandFailed(e);
        }

        return ResponseTypeFactory.success();
	}

    private void addNs(DomainOperation operation, NsType ns) throws CommandUseError, ParamValuePolicyErrorException {
        if (!operation.containsNameserver(ns.getNsName())) {
            final List<String> ipAddresses = ns.getNsAddr();
            operation.addNameserverChange(new NameserverChange(
                    new SimpleDomainFieldChange<String>(null, ns.getNsName()),
                    new SimpleDomainFieldChange<String>(null, isEmpty(ipAddresses) ? null : ipAddresses.get(0))
            ));
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_ALREADY_DELEGATED_TO_HOST_TO_ADD, new Value("nsName", TICKET_NAMESPACE, ns.getNsName()));
        }
    }


	private void removeNs(DomainOperation operation, NsNameType ns) throws ParamValuePolicyErrorException {
		if (operation.containsNameserver(ns.getNsName())) {
            operation.removeNameserverChange(ns.getNsName());
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_NOT_DELEGATED_TO_HOST_TO_REMOVE, new Value("nsName", TICKET_NAMESPACE, ns.getNsName()));
        }
	}


	private void addContact(List<SimpleDomainFieldChange<Contact>> list, ContactType c, int maxSize) throws CommandUseError, ParamValuePolicyErrorException {
		// TODO: what error should be used?
		if (list.size() == maxSize)
			throw new CommandUseError();
		SimpleDomainFieldChange<Contact> cvo = findContact(list, c);
		if (cvo != null) {
			throw new ParamValuePolicyErrorException(c.getType() == ContactAttrType.ADMIN ? ReasonCode.ADMINC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET : ReasonCode.TECHC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET, new Value("contact", TICKET_NAMESPACE, c.getValue()));
		} else { 
			cvo = findContact(list, null); // find the first empty place to add this contact
            if (cvo != null)
                cvo.setNewValue(new Contact(c.getValue()));
            switch (c.getType()) {
                case ADMIN:
                    adminCount++;
                    break;
                case TECH:
                    techCount++;
                    break;
            }
        }
	}

	private void removeContact(List<SimpleDomainFieldChange<Contact>> list, ContactType c) throws ParamValuePolicyErrorException {
		SimpleDomainFieldChange<Contact> cvo = findContact(list, c);
		if (cvo != null) {
            cvo.setNewValue(null);
            switch (c.getType()){
                case ADMIN:
                    adminCount--;
                    break;
                case TECH:
                    techCount--;
                    break;
            }
        } else
			throw new ParamValuePolicyErrorException(c.getType() == ContactAttrType.ADMIN ? ReasonCode.ADMINC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET : ReasonCode.TECHC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET, new Value("contact", TICKET_NAMESPACE, c.getValue()));
	}

	
	private SimpleDomainFieldChange<Contact> findContact(List<SimpleDomainFieldChange<Contact>> list, ContactType c) {
		for (int i = 0; i < list.size(); i++) {
			SimpleDomainFieldChange<Contact> cvo = list.get(i);
			if (c == null && (cvo.getNewValue() == null || isEmpty(cvo.getNewValue().getNicHandle()))) {
				return cvo;
			} else if (cvo.getNewValue() != null && c != null && cvo.getNewValue().getNicHandle().equals(c.getValue())) {
				return cvo;
			}
		}
		return null;
	}

    private List<NsType> toNsTypeList(List<NameserverChange> nsChangeList) {
        List<NsType> ret = new ArrayList<NsType>();
        for(NameserverChange ns : nsChangeList) {
            if(!isEmpty(ns.getName().getNewValue())) {
                NsType nsType = new NsType();
                nsType.setNsName(ns.getName().getNewValue());
                nsType.getNsAddr().add(ns.getIpAddress().getNewValue());
                ret.add(nsType);
            }
        }
        return ret;
    }

    private List<String> toStringList(List<SimpleDomainFieldChange<Contact>> inList) {
        List<String> ret = new ArrayList<String>();
        for(SimpleDomainFieldChange<Contact> c : inList) {
            if(c.getNewValue() != null && !isEmpty(c.getNewValue().getNicHandle()))
                ret.add(c.getNewValue().getNicHandle());
        }
        return ret;
    }

    private void calculateNsAndContactsCount(DomainOperation domainOperation) {
        List<SimpleDomainFieldChange<Contact>> adminList = domainOperation.getAdminContactsField();
        List<SimpleDomainFieldChange<Contact>> techList = domainOperation.getTechContactsField();
        int adminCount = 0;
        int techCount = 0;
        for (SimpleDomainFieldChange<Contact> c : adminList) {
            if (c.getNewValue() != null && !isEmpty(c.getNewValue().getNicHandle()))
                adminCount++;
        }
        for (SimpleDomainFieldChange<Contact> c : techList) {
            if (c.getNewValue() != null && !isEmpty(c.getNewValue().getNicHandle()))
                techCount++;
        }
        this.adminCount = adminCount;
        this.techCount = techCount;
    }
}
