package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.ContactType;
import ie.domainregistry.ieapi_domain_1.ModifyType;
import ie.domainregistry.ieapi_domain_1.NsType;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainModifyCommandHandler extends AbstractDomainCommandHandler<ModifyType> {

    @Deprecated
    public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {

        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);

        Domain domain = findDomain(auth.getUser(), command.getName());

        List<String> oldDomainAdminContacts = new ArrayList<String>();
        List<String> oldDomainTechContacts = new ArrayList<String>();
        List<String> newDomainAdminContacts = new ArrayList<String>();
        List<String> newDomainTechContacts = new ArrayList<String>();
        for (Contact c : domain.getAdminContacts()) {
            oldDomainAdminContacts.add(c.getNicHandle());
            newDomainAdminContacts.add(c.getNicHandle());
        }
        for (Contact c : domain.getTechContacts()) {
            oldDomainTechContacts.add(c.getNicHandle());
            newDomainTechContacts.add(c.getNicHandle());
        }
        if (isCommandAddContact(command)) {
            for (ContactType ct : command.getAdd().getContact()) {
                switch (ct.getType()) {
                    case ADMIN:
                        if (oldDomainAdminContacts.contains(ct.getValue()))
                            throw new DataManagementPolicyViolationException(ReasonCode.ADMINC_TO_ADD_ALREADY_ASSOCIATED_WITH_DOMAIN, new Value("contact", DOMAIN_NAMESPACE, ct.getValue()));
                        newDomainAdminContacts.add(ct.getValue());
                        break;
                    case TECH:
                        if (oldDomainTechContacts.contains(ct.getValue()))
                            throw new DataManagementPolicyViolationException(ReasonCode.TECHC_TO_ADD_ALREADY_ASSOCIATED_WITH_DOMAIN, new Value("contact", DOMAIN_NAMESPACE, ct.getValue()));
                        newDomainTechContacts.add(ct.getValue());
                        break;
                }
            }
        }
        if (isCommandRemoveContact(command)) {
            for (ContactType ct : command.getRem().getContact()) {
                switch (ct.getType()) {
                    case ADMIN:
                        if (!oldDomainAdminContacts.contains(ct.getValue()))
                            throw new DataManagementPolicyViolationException(ReasonCode.ADMINC_TO_REMOVE_NOT_ASSOCIATED_WITH_DOMAIN, new Value("contact", DOMAIN_NAMESPACE, ct.getValue()));
                        newDomainAdminContacts.remove(ct.getValue());
                        break;
                    case TECH:
                        if (!oldDomainTechContacts.contains(ct.getValue()))
                            throw new DataManagementPolicyViolationException(ReasonCode.TECHC_TO_REMOVE_NOT_ASSOCIATED_WITH_DOMAIN, new Value("contact", DOMAIN_NAMESPACE, ct.getValue()));
                        newDomainTechContacts.remove(ct.getValue());
                        break;
                }
            }
        }
        if (!isContactModified(command)) {
            newDomainAdminContacts = null;
            newDomainTechContacts = null;
        }

        List<Nameserver> newNameservers = null;
        if (isCommandModifyNameservers(command)) {
            List<NsType> domainNss = DomainValidationHelper.prepareNssValidateDelegation(command, domain.getNameservers(), DOMAIN_NAMESPACE);
            newNameservers = DomainConversionHelper.toNameserverList(domainNss);
        }

        RenewalMode newRenewalMode = null;
        if (isCommandModifyRenewalStatus(command)) {
            newRenewalMode = RenewalMode.forName(command.getChg().getRenewalMode().value());
        }

        String domainHolder = null;
        String domainClass = null;
        String domainCategory= null;
        if (isCommandModifyHolder(command)) {
            domainHolder = prepareField(command.getChg().getHolder().getHolderName().getValue());
            domainClass = prepareField(command.getChg().getHolder().getClazz());
            domainCategory = prepareField(command.getChg().getHolder().getHolderName().getCategory());
//            newTicket.setRequestersRemark(command.getChg().getHolder().getHolderRemarks());
        }

        Long ticketId = null;
        try {
            ticketId = getCommonAppService().modifyDomain(auth.getUser(), command.getName(), domainHolder, domainClass, domainCategory, newDomainAdminContacts, newDomainTechContacts, newNameservers, newRenewalMode, "Domain modify.");
        } catch (Exception e) {
            throw mapException(e);
        }

        return ticketId == null ?
                ResponseTypeFactory.successNoRes() :
                ResponseTypeFactory.success(prepareResponse(domain.getName(), ticketId));
    }

    private boolean isCommandAddContact(ModifyType command) {
        return command.getAdd() != null && !Validator.isEmpty(command.getAdd().getContact());
    }

    private boolean isCommandRemoveContact(ModifyType command) {
        return command.getRem() != null && !Validator.isEmpty(command.getRem().getContact());
    }

    private boolean isContactModified(ModifyType command) {
        return isCommandAddContact(command) || isCommandRemoveContact(command);
    }

    private boolean isCommandModifyNameservers(ModifyType command) {
        return (command.getAdd() != null
                && !Validator.isEmpty(command.getAdd().getNs()))
                || (command.getRem() != null
                && !Validator.isEmpty(command.getRem().getNs()));
    }

    private boolean isCommandModifyRenewalStatus(ModifyType command) {
        return command.getChg() != null && command.getChg().getRenewalMode() != null;
    }

    private boolean isCommandModifyHolder(ModifyType command) {
        return command.getChg() != null && command.getChg().getHolder() != null;
    }

    private String prepareField(String value) {
        return value == null ? null : value;
    }
}
