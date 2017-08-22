package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.ContactType;
import ie.domainregistry.ieapi_domain_1.TransferType;
import ie.domainregistry.ieapicom_1.ContactAttrType;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainTransferCommandHandler extends AbstractDomainCommandHandler<TransferType> {

    public ResponseType handle(AuthData auth, TransferType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);
        ResellerDefaults defaults = null;

        if (isEmptyTechContactElement(command) || isAllDefaultsUse(command)) {
            try {
                defaults = getNicHandleAppService().getDefaults(auth.getUser(), auth.getUsername());
            } catch (DefaultsNotFoundException e) {
                throw new ObjectDoesNotExistException(ReasonCode.RESELLER_DEFAULTS_NOT_DEFINED);
            }
        }

        try {
        	TransferRequestVO transferRequestVO = prepareTransferRequest(command, defaults);
            long ticketId = getCommonAppService().transfer(auth.getUser(), transferRequestVO, null);
            return ResponseTypeFactory.success(prepareResponse(command.getName().toLowerCase(), ticketId));
        } catch (Exception e) {
            throw mapException(e);
        }
    }

    private boolean isAllDefaultsUse(TransferType command) {
        boolean isDefaultElement = command.isDefaults() != null && command.isDefaults();
        return isDefaultElement || (isEmptyTechContactElement(command) && Validator.isEmpty(command.getNs()));
    }

    private boolean isEmptyTechContactElement(TransferType command) {
        if (Validator.isEmpty(command.getContact()))
            return true;
        boolean ret = true;
        for (ContactType contact : command.getContact()) {
            if (contact.getType().equals(ContactAttrType.TECH))
                ret = false;
        }
        return ret;
    }

    private TransferRequestVO prepareTransferRequest(TransferType command, ResellerDefaults defaults) throws DomainNotFoundException {
        TransferRequestVO transferReq = new TransferRequestVO();

        transferReq.setDomainName(command.getName());
        transferReq.setAuthCode(command.getAuthCode());

        if (isAllDefaultsUse(command)) {
            useAllDefaults(transferReq, defaults);
        } else if (isEmptyTechContactElement(command)) {
            useDefaultTechContactAndCommandNs(command, transferReq, defaults);
        } else if (Validator.isEmpty(command.getNs())) {
            useCommandTechContact(command, transferReq);
        } else {
            useCommmandNsAndTechContact(command, transferReq);
        }

        transferReq.setRequestersRemark("Domain transfer request - API.");   
        
        if (!isCharityDomain(command.getName()) && command.getPeriod() != null) {
        	transferReq.setPeriod(command.getPeriod().getValue());
        	transferReq.setPeriodType(TicketRequest.PeriodType.valueOf(command.getPeriod().getUnit().name()));
        }
        
        if (command.isStatus() != null) {
            transferReq.setStatus(command.isStatus());
        }

        return transferReq;
    }

	private void useAllDefaults(TransferRequestVO transferRequest, ResellerDefaults defaults) {
        transferRequest.setTechContactNicHandle(defaults.getTechContactId());
        final List<String> defaultNameservers = defaults.getNameservers();
        List<NameserverVO> nameservers = new ArrayList<NameserverVO>(defaultNameservers.size());
        for (String nsName : defaultNameservers) {
            nameservers.add(new NameserverVO(nsName, null));
        }
        transferRequest.setNameservers(nameservers);
    }

    private void useDefaultTechContactAndCommandNs(TransferType command, TransferRequestVO transferRequest, ResellerDefaults defaults) {
        transferRequest.setTechContactNicHandle(defaults.getTechContactId());
        DomainConversionHelper.updateNs(transferRequest, command.getNs());
    }

    private void useCommandTechContact(TransferType command, TransferRequestVO transferRequest) {
        for (ContactType contact : command.getContact()) {
            switch (contact.getType()) {
                case TECH:
                    transferRequest.setTechContactNicHandle(contact.getValue());
                    break;
            }
        }
    }

    private void useCommmandNsAndTechContact(TransferType command, TransferRequestVO transferRequest) {
        useCommandTechContact(command, transferRequest);
        DomainConversionHelper.updateNs(transferRequest, command.getNs());
    }
}
