package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_domain_1.AppDataType;
import ie.domainregistry.ieapi_domain_1.ContactType;
import ie.domainregistry.ieapi_domain_1.CreateType;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.commons.exceptions.CreateDomainException;
import pl.nask.crs.app.commons.exceptions.CreateTicketException;
import pl.nask.crs.app.commons.exceptions.DomainIncorrectStateException;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.commons.exceptions.TransferDomainException;
import pl.nask.crs.app.commons.impl.NameserversValidationException;
import pl.nask.crs.app.commons.register.CharityRegistrationNotPossibleException;
import pl.nask.crs.app.tickets.exceptions.ContactSyntaxException;
import pl.nask.crs.app.tickets.exceptions.DomainAlreadyManagedByResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainDeletionPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainInNRPException;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainNameExistsOrPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainNameMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainNameSyntaxException;
import pl.nask.crs.app.tickets.exceptions.DomainTransaferPendingException;
import pl.nask.crs.app.tickets.exceptions.DuplicatedContactException;
import pl.nask.crs.app.tickets.exceptions.DuplicatedNameserverException;
import pl.nask.crs.app.tickets.exceptions.GlueNotAllowedException;
import pl.nask.crs.app.tickets.exceptions.GlueRequiredException;
import pl.nask.crs.app.tickets.exceptions.HolderCategoryMandatoryException;
import pl.nask.crs.app.tickets.exceptions.HolderClassMandatoryException;
import pl.nask.crs.app.tickets.exceptions.HolderRemarkTooLongException;
import pl.nask.crs.app.tickets.exceptions.IpSyntaxException;
import pl.nask.crs.app.tickets.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.app.tickets.exceptions.TooFewAdminContactsException;
import pl.nask.crs.app.tickets.exceptions.TooFewNameserversException;
import pl.nask.crs.app.tickets.exceptions.TooFewTechContactsException;
import pl.nask.crs.app.tickets.exceptions.TooManyAdminContactsException;
import pl.nask.crs.app.tickets.exceptions.TooManyNameserversException;
import pl.nask.crs.app.tickets.exceptions.TooManyTechContactsException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.exceptions.AuthorizationErrorException;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.DataManagementPolicyViolationException;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ObjectExistException;
import pl.nask.crs.iedrapi.exceptions.ObjectStatusProhibitsOperationException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ParamValueSyntaxErrorException;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.RequiredParameterMissingException;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.xml.Constants;

/**
 * @author: Marcin Tkaczyk
 */
public abstract class AbstractDomainCommandHandler<T> extends AbstractCommandHandler implements APICommandHandler<T> {

    public static final int DEFAULT_PERIOD = 1;
    public static final String DOMAIN_NAMESPACE = Constants.IEAPI_DOMAIN_NAMESPACE;    
    public static final Logger LOG = Logger.getLogger(AbstractDomainCommandHandler.class);

    public void validateAccountId(long id, Domain domain) throws IedrApiException {
        if (id != domain.getResellerAccount().getId())
            throw new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name", DOMAIN_NAMESPACE, domain.getName()));
    }

    public Domain findDomain(AuthenticatedUserVO user, String name) throws IedrApiException {
        if (Validator.isEmpty(name))
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);
        try {
            Domain d = getDomainAppService().viewPlain(user, name);
            if (d.getDsmState().getNRPStatus() == NRPStatus.Deleted) {
            	throw new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value("name", DOMAIN_NAMESPACE, name));
            } else {
            	return d;
            }
        } catch (DomainNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value("name", DOMAIN_NAMESPACE, name));
        } catch (AccessDeniedException e) {
        	throw new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name", DOMAIN_NAMESPACE, name));
        }
    }
    
    public boolean isCharityDomain(String domainName) throws DomainNotFoundException {
    	Domain d = getHelper().getDomainUnsafe(domainName);
    	return (d.getDsmState().getDomainHolderType() == DomainHolderType.Charity);
    }

	public void checkDomainContacts(AuthenticatedUser user, long accountId, List<String> contacts, int min, int max, ReasonCode minReason, ReasonCode maxReason) throws IedrApiException, AccessDeniedException {
    	if (contacts.size() < min)
    		throw new DataManagementPolicyViolationException(minReason);
    	if (contacts.size() > max)
    		throw new DataManagementPolicyViolationException(maxReason);

    	Set<String> contactsSet = new HashSet<String>();
    	for (String c : contacts) {
    		if (!contactsSet.add(c))
    			throw new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_CONTACT_ID, new Value("contact", DOMAIN_NAMESPACE, c));
    		if (!c.endsWith("-IEDR"))
    			throw new ParamValueSyntaxErrorException(ReasonCode.CONTACT_ID_SYNTAX_ERROR, new Value("contact", DOMAIN_NAMESPACE, c));

    		NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
    		criteria.setNicHandleId(c);
    		criteria.setAccountNumber(accountId);
    		LimitedSearchResult<NicHandle> searchResult =  getNicHandleAppService().search(user, criteria, 0, 10, null);
    		if (searchResult.getTotalResults() == 0)
    			throw new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact", DOMAIN_NAMESPACE, c));
    	}
    }

    protected IedrApiException mapException(Exception ee) {
        try {
            throw ee;
        } catch (HolderClassNotExistException e) {
            return new ParameterValueRangeErrorException(ReasonCode.HOLDER_CLASS_DOESNT_EXIST, new Value("class", "holder", e.getDomainHolderClass()), e);
        } catch (HolderCategoryNotExistException e) {
            return new ParameterValueRangeErrorException(ReasonCode.HOLDER_CATEGORY_DOESNT_EXIST, new Value("class", "holder", e.getDomainHolderCategory()), e);
        } catch (ClassDontMatchCategoryException e) {
            return new ParameterValueRangeErrorException(ReasonCode.CLASS_DONT_MATCH_CATEGORY, new Value("class", "holder", e.getDomainHolderClass()), e);
        } catch (ClassCategoryPermissionException e) {
            return new ParamValuePolicyErrorException(ReasonCode.PERMISSION_DENIED_TO_AUTOCREATE_DOMAIN_USING_THIS_CATEGORY, e);
        } catch (DomainNameExistsOrPendingException e) {
            return new ObjectExistException(ReasonCode.DOMAIN_NAME_EXISTS_OR_PENDING, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainAlreadyManagedByResellerException e) {
            return new DataManagementPolicyViolationException(ReasonCode.DOMAIN_ALREADY_MANAGED_BY_RESELLER, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
//        } catch (TicketInUseException e) {
//            return new DataManagementPolicyViolationException(ReasonCode.TICKET_IN_USE,  new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
//        } catch (TicketManagedByAnotherResellerException e) {
//            return new DataManagementPolicyViolationException(ReasonCode.TICKET_IS_MANAGED_BY_ANOTHER_RESELLER,  new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (TooFewNameserversException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_DNS, e);
        } catch (TooManyNameserversException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_DNS, e);
        } catch (DuplicatedNameserverException e) {
            return new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_DNS_NAME, new Value("nsName", DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (NameserverNameSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR, new Value("nsName", DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (IpSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.IP_SYTAX_ERROR, new Value("nsAddr", DOMAIN_NAMESPACE, e.getIpAddress()), e);
        } catch (GlueNotAllowedException e) {
            return new DataManagementPolicyViolationException(ReasonCode.GLUE_NOT_ALLOWED, new Value("nsName", DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (GlueRequiredException e) {
            return new DataManagementPolicyViolationException(ReasonCode.GLUE_IS_REQUIRED, new Value("nsName", DOMAIN_NAMESPACE, e.getNsName()), e);
        } catch (TooFewAdminContactsException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_ADMIN_CONTACTS, e);
        } catch (TooFewTechContactsException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_TECH_CONTACTS, e);
        } catch (TooManyAdminContactsException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_ADMIN_CONTACTS, e);
        } catch (TooManyTechContactsException e) {
            return new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_TECH_CONTACTS, e);
        } catch (DuplicatedContactException e) {
            return new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_CONTACT_ID, new Value("contact", DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (ContactSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.CONTACT_ID_SYNTAX_ERROR, new Value("contact", DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (NicHandleNotFoundException e) {
            return new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact", DOMAIN_NAMESPACE, e.getNicHandleId()), e);
        } catch (pl.nask.crs.ticket.exceptions.NicHandleNotFoundException e) {
            return new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact", DOMAIN_NAMESPACE, e.getNicHandle()), e);
        } catch (DomainNameSyntaxException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.DOMAIN_NAME_SYNTAX_ERROR, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainNameMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY, e);
        } catch (HolderClassMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.HOLDER_CLASS_MANDATORY, e);
        } catch (HolderCategoryMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.HOLDER_CATEGORY_MANDATORY, e);
        } catch (DomainHolderMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY, e);
        } catch (HolderRemarkTooLongException e) {
            return new RequiredParameterMissingException(ReasonCode.HOLDER_REMARK_TOO_LONG, e);
        } catch (DomainManagedByAnotherResellerException e) {
            return new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainDeletionPendingException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_DELETION_PENDING, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainModificationPendingException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_PENDING, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainTransaferPendingException e) {
               return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_TRANSFER_PENDING, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainNotFoundException e) {
            return new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (NotAdmissiblePeriodException e) {
            return new ParamValuePolicyErrorException(ReasonCode.PERIOD_VALUE_NOT_ADMISSIBLE, e);
        } catch (CharityRegistrationNotPossibleException e) {
            return new ParamValuePolicyErrorException(ReasonCode.CHARITY_REGISTRATION_NOT_POSSIBLE, e);
        } catch (DomainNotBillableException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIncorrectStateException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainInNRPException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_MODIFICATION_NOT_POSSIBLE, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIsCharityException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIllegalStateException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION, new Value("name", DOMAIN_NAMESPACE, e.getDomain().getName()), e);
        } catch (HostNotConfiguredException e) {
            return new ParamValuePolicyErrorException(ReasonCode.HOST_NOT_CONFIGURED_FOR_DOMAIN, e.getFatalMessage());
        } catch (AccessDeniedException e) {
            return new AuthorizationErrorException(e);
        } catch (DomainIsNotCharityException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.CHARITY_TRANSFER_NOT_POSSIBLE, new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (InvalidAuthCodeException e) {
            return new AuthorizationErrorException(ReasonCode.INVALID_AUTHCODE, e);
        } catch (NameserversValidationException e) {
            return mapException(e.retrieveFirstCause());
        } catch (Exception e) {
            return new CommandFailed(e);
        }
    }

    public IedrApiException mapCreateTicketException(CreateTicketException cte) {
    	return mapException(cte.getExceptionCause());
    }

    public IedrApiException mapTransferDomainException(TransferDomainException tde) {
    	return mapException(tde.getExceptionCause());
    }

    public IedrApiException mapCreateDomainException(CreateDomainException cde) {
    	return mapException(cde.getExceptionCause());
    }
    
    public IedrApiException mapPaymentException(PaymentException e) {
    	return new CommandFailed(e);
    }       
    
    public RegistrationRequestVO prepareRegistrationRequest(CreateType command) {
		RegistrationRequestVO request = new RegistrationRequestVO();
        request.setDomainName(command.getName());
        request.setDomainHolder(command.getHolder().getHolderName().getValue());
        request.setDomainHolderClass(command.getHolder().getClazz());
        request.setDomainHolderCategory(command.getHolder().getHolderName().getCategory());

        String remark = command.getHolder().getHolderRemarks();
        if (remark == null || remark.equals("")) {
            remark = "Ticket created via API";
        }
        request.setRequestersRemark(remark);

        int adminCount = 0;
        for (ContactType contact : command.getContact()) {
            switch (contact.getType()) {
                case ADMIN:
                    if (adminCount == 0) {
                        request.setAdminContact1NicHandle(contact.getValue());
                    }
                    if (adminCount == 1) {
                        request.setAdminContact2NicHandle(contact.getValue());
                    }
                    adminCount++;
                    break;
                case TECH:
                    request.setTechContactNicHandle(contact.getValue());
                    break;
            }
        }

        DomainConversionHelper.updateNs(request, command.getNs());

        if (command.getPeriod() != null) {
        	request.setPeriod(command.getPeriod().getValue());
        	request.setPeriodType(PeriodType.valueOf(command.getPeriod().getUnit().name()));
        }

        boolean isCharity = !Validator.isEmpty(command.getChy());
        if (isCharity) {
            request.setCharityCode(command.getChy());
        }
        
        return request;
		
	}

    public AppDataType prepareResponse(String domainName, long ticketId) {
        AppDataType res = new AppDataType();
        res.setName(domainName);
        res.setAppNumber(BigInteger.valueOf(ticketId));

        Calendar c = Calendar.getInstance();
        res.setAppDate(c.getTime());
        c.add(Calendar.DATE, 27);
        res.setExDate(c.getTime());
        return res;
    }

}
